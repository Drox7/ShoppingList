package com.drox7.myapplication.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainDialog(
    dialogController: DialogController
) {
    val titleColor = Color(android.graphics.Color.parseColor(dialogController.titleColor.value))
    val regexFloat = Regex("^[0-9]+(.[0-9]{0,2})?\$")
    val scope = rememberCoroutineScope()
    //val state = remember { mutableStateOf(TextFieldValue(""))
    //val regexFloat = Regex("^([0-9][0-9]*)+(.[0-9]{0,2})?\$")
    if (dialogController.openDialog.value) {
        AlertDialog(
            containerColor = colorScheme.onPrimary,
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
            title = null,
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (dialogController.showEditTableText.value)
                        TextField(
                            value = dialogController.editTableText.value,
                            onValueChange = {
                                dialogController.onDialogEvent(DialogEvent.OnTextChange(it))
                            },
                            // label = {
                            //     Text(text = "")
                            //},
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorScheme.background,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = titleColor,
                                //focusedLabelColor = BlueLight
                            ),
                            shape = RoundedCornerShape(9.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = titleColor,
                                fontSize = 16.sp,
                                //fontWeight = FontWeight.Bold,
                            )
                        )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (dialogController.showEditSumText.value)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField( //planSum
                                value = dialogController.planSumTextFieldValue.value,

                                onValueChange = {
                                    if (it.text.matches(regexFloat)) {
                                        if (!it.text.contains(","))
                                            dialogController.onDialogEvent(
                                                DialogEvent.OnPlanSumChange(it)
                                            )
                                    }
                                },
                                modifier = Modifier
                                    .onFocusChanged {
                                            focusState ->
                                        if (focusState.isFocused) {
                                            scope.launch {
                                                delay(10)
                                                val text = dialogController.planSumTextFieldValue.value.text
                                                dialogController.planSumTextFieldValue.value =
                                                    dialogController.planSumTextFieldValue.value.copy(
                                                        selection = TextRange(0, text.length)
                                                    )
                                            }
                                           // keepWholeSelection = true
                                        }
                                    }
                                    .padding(end = 5.dp)
                                    .weight(0.5f),

                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                               // value = dialogController.editPlanSumText.value,



                                label = {
                                    Text(text = "Сумма план(₽)", fontSize = 12.sp)
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = colorScheme.background,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = titleColor,
                                    //focusedLabelColor = BlueLight
                                ),
                                shape = RoundedCornerShape(9.dp),
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = titleColor,
                                    fontSize = 16.sp,
                                    //fontWeight = FontWeight.Bold,
                                )
                            )

                            TextField( //actualSum
                                modifier = Modifier
                                    .onFocusChanged {
                                            focusState ->
                                        if (focusState.isFocused) {
                                            scope.launch {
                                                delay(10)
                                                val text = dialogController.actualSumTextFieldValue.value.text
                                                dialogController.actualSumTextFieldValue.value =
                                                    dialogController.actualSumTextFieldValue.value.copy(
                                                        selection = TextRange(0, text.length)
                                                    )
                                            }
                                            // keepWholeSelection = true
                                        }
                                    }
                                    .weight(0.5f),
                                value = dialogController.actualSumTextFieldValue.value,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    if (it.text.matches(regexFloat)) {
                                        if (!it.text.contains(","))
                                            dialogController.onDialogEvent(
                                                DialogEvent.OnActualSumChange(it)
                                            )
                                    }

                                },
                                label = {
                                    Text(text = "Сумма факт(₽)", fontSize = 12.sp)
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = colorScheme.background,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = titleColor,
                                    //focusedLabelColor = BlueLight
                                ),
                                shape = RoundedCornerShape(9.dp),
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = titleColor,
                                    fontSize = 16.sp,
                                    //fontWeight = FontWeight.Bold,
                                )
                            )
                        }

                }
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnConfirm)
                }) {
                    Text(
                        text = "Ok",
                        style = TextStyle(
                            color = titleColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )

                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnCancel)
                }) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            color = titleColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }

            },
            shape = RoundedCornerShape(20.dp),
        )
    }
}