package com.drox7.myapplication.new_note_screen

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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.DarkText

@Preview(showBackground = true)
@Composable
fun NewNoteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = "Tit",
                        onValueChange = {

                        },
                        label = {
                            Text(
                                text = "Title...",
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedLabelColor = Color.Transparent,
                            unfocusedIndicatorColor = BlueLight
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                    )
                    IconButton(
                        onClick = {}
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
                modifier = Modifier.fillMaxSize(),
                value = "",
                onValueChange = {
                },
                label = {
                    Text(
                        text = "Description",
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = DarkText
                )
            )
        }
    }
}