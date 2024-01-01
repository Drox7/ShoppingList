package com.drox7.myapplication.add_item_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.R
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{uiEven ->
            when(uiEven){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEven.message
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState,snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState){data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight,
                actionColor = Color.White,
                shape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
            )

        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            Card(
                shape = RoundedCornerShape(bottomStart = 10.dp,bottomEnd = 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(colorScheme.onPrimary)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = {text ->
                                        viewModel.onEvent(AddItemEvent.OnTextChange(text))
                        },
                        label = {
                            Text(
                                text = "New item",
                                fontSize = 12.sp,
                               // color = BlueLight
                            )

                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colorScheme.onPrimary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = BlueLight
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = BlueLight
                        ),
                        singleLine = true
                    )
                    IconButton(

                        onClick = {
                            viewModel.onEvent(AddItemEvent.OnItemSave)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "ADD",
                            tint = BlueLight
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        // top = 3.dp,
                        start = 2.dp,
                        end = 2.dp
                    )
            ) {
                if(itemsList!=null){
                    items(itemsList.value){item ->
                        UiAdItem(item = item, onEvent = {event ->
                            viewModel.onEvent(event)
                        })
                    }
                }
            }
        }
        MainDialog(dialogController = viewModel)
        if (itemsList?.value?.isEmpty() == true) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "EMPTY",
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}