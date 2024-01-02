package com.drox7.myapplication.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDialog(
    dialogController: DialogController
) {
    val titleColor = Color(android.graphics.Color.parseColor(dialogController.titleColor.value))
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