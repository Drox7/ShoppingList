package com.drox7.myapplication.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.R
import com.drox7.myapplication.main_screen.UiTopBar
import com.drox7.myapplication.ui.theme.GrayLight
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary
import com.drox7.myapplication.utils.SwipeToDeleteContainer
import com.drox7.myapplication.utils.getScaleText


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingsListScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {

    val list = viewModel.colorItemListState.value
    val categoryListState = viewModel.listCategoryFlow.collectAsState(initial = emptyList())
    val unitListState = viewModel.listUnitFlow.collectAsState(initial = emptyList())
    val titleColor = Color(android.graphics.Color.parseColor(viewModel.titleColor))
    Scaffold(
        topBar = {
            UiTopBar(
                titleColor = GrayLight,
                onClick = { onPopBackStack() },
                titleText = "Настройки",
                iconVector = Icons.Filled.ArrowBack
            )
        }) {

        Column(
            // horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, start = 10.dp, end = 10.dp, bottom = 60.dp)
        ) {
            Text(
                text = "Тема:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp * (getScaleText(viewModel.scaleText.floatValue)),
                color = titleColor
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                items(list) { item ->
                    UiColorItem(item) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,

                    ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    //.background(MaterialTheme.colorScheme.onPrimary)
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp, top = 20.dp)


            ) {

                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                //.background(MaterialTheme.colorScheme.onPrimary)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .scale(scaleY = 0.85f, scaleX = 1f)
                                    .weight(1f),
                                value = viewModel.itemCategoryText.value,
                                onValueChange = { text ->
                                    viewModel.onEvent(SettingsEvent.OnTextCategoryChange(text))
                                },
                                placeholder = {
                                    Text(
                                        text = "Категория",
                                        fontSize = 14.sp * (getScaleText(viewModel.scaleText.floatValue)),
                                        color = md_theme_light_tertiary
                                        //color = titleColor
                                    )

                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colorScheme.background,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = titleColor
                                ),
                                textStyle = TextStyle(
                                    fontSize = 14.sp * (getScaleText(viewModel.scaleText.floatValue)),
                                    color = titleColor
                                ),
                                singleLine = true
                            )
                            IconButton(

                                onClick = {
                                    viewModel.onEvent(SettingsEvent.OnCategoryItemSave)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_icon),
                                    contentDescription = "ADD",
                                    tint = titleColor
                                )
                            }
                        }
                        LazyColumn(
                            contentPadding = PaddingValues(end = 10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            items(
                                categoryListState.value,
                                key = { it.id ?: 0 }
                            ) { item ->
                                SwipeToDeleteContainer(
                                    item = item,
                                    onDelete = {
                                        viewModel.onEvent(SettingsEvent.OnDeleteCategory(item))
                                    })
                                {
                                    Text(
                                        text = item.name,
                                        //fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.onPrimary)
                                            .padding(bottom = 3.dp),
//                                        .pointerInput(Unit) {
//                                            detectTapGestures(
//                                                onLongPress = {
//                                                    viewModel.onEvent(
//                                                        SettingsEvent.OnDeleteCategory(item)
//                                                    )
//                                                }
//                                            )
//                                        },

                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize * (getScaleText(
                                            viewModel.scaleText.floatValue
                                        ))
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                //.background(MaterialTheme.colorScheme.onPrimary)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .scale(scaleY = 0.85f, scaleX = 1f),
                                value = viewModel.itemUnitText.value,
                                onValueChange = { text ->
                                    viewModel.onEvent(SettingsEvent.OnTextUnitChange(text))
                                },
                                placeholder = {
                                    Text(
                                        text = "Еденица",
                                        fontSize = 14.sp * (getScaleText(viewModel.scaleText.floatValue)),
                                        color = md_theme_light_tertiary
                                        //color = titleColor
                                    )

                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colorScheme.background,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = titleColor
                                ),
                                textStyle = TextStyle(
                                    fontSize = 14.sp * (getScaleText(viewModel.scaleText.floatValue)),
                                    color = titleColor
                                ),
                                singleLine = true
                            )
                            IconButton(

                                onClick = {
                                    viewModel.onEvent(SettingsEvent.OnUnitItemSave)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_icon),
                                    contentDescription = "ADD",
                                    tint = titleColor
                                )
                            }
                        }
                        LazyColumn(
                            contentPadding = PaddingValues(end = 10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            items(
                                unitListState.value,
                                key = { it.id ?: 0 }
                            ) { item ->
                                SwipeToDeleteContainer(
                                    item = item,
                                    onDelete = {
                                        viewModel.onEvent(SettingsEvent.OnDeleteUnit(item))
                                    })
                                {
                                    Text(
                                        text = item.localName,
                                        // fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.onPrimary)
                                            .padding(bottom = 3.dp),
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize * (getScaleText(
                                            viewModel.scaleText.floatValue
                                        ))
                                    )
                                }
                            }
                        }
                    }

                }
                SliderScaleText(titleColor, viewModel.scaleText.floatValue) { event ->
                    viewModel.onEvent(event)
                }
            }

        }
    }
}

