package com.drox7.myapplication.new_note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }

        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = colorScheme.onPrimary,
        snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState){data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight,
                actionColor = Color.White
            )

        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            // .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Card(
                modifier = Modifier

                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .background(colorScheme.onPrimary)
                            .fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.title,
                            onValueChange = { text ->
                                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                            },
//                        label = {
//                            Text(
//                                text = "Title...",
//                                fontSize = 14.sp
//                            )
//                        },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorScheme.onPrimary,
                                focusedLabelColor = Color.Transparent,
                                unfocusedIndicatorColor = BlueLight,
                                focusedIndicatorColor = BlueLight,
                                cursorColor = BlueLight
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onBackground
                            )

                        )
                        IconButton(
                            // modifier = Modifier.background(colorScheme.background),
                            onClick = {
                                viewModel.onEvent(NewNoteEvent.OnSave)
                            }
                        ) {
                            Icon(

                                painter = painterResource(id = R.drawable.save),
                                contentDescription = "Save",
                                tint = BlueLight
                            )
                        }
                    }
                }
                TextField(
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier

                        .fillMaxSize(),
                    value = viewModel.description,
                    onValueChange = { text ->
                        viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                    },
//                label = {
//                    Text(
//                        text = "Description...",
//                        fontSize = 14.sp
//                    )
//                },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorScheme.onPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = colorScheme.onBackground
                    )
                )
            }
        }
    }
}