package com.drox7.myapplication.note_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary

@Preview(showBackground = true)
@Composable
fun UiNoteItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, top = 3.dp, end = 3.dp)
            .clickable { }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 10.dp
                        )
                        .weight(1f),
                    text = "Note 1",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        end = 10.dp
                    ),
                    text = "12/12/2023 12:00",
                    color = BlueLight,
                    fontSize = 12.sp
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            start = 10.dp,
                            bottom = 10.dp
                        )
                        .weight(1f),
                    text = "Бежит ежик и хохочет, ежику травка письку счекочет!" +
                            "Колобок бежит за ежиком, а ежику похуй он хохочет хаха" +
                            " Но тут пришла лиса, рыжая пизда",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = md_theme_light_tertiary
                )
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription ="delete",
                        tint = Red
                    )
                }
            }
        }
    }
}