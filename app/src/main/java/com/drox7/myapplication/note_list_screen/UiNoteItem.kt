package com.drox7.myapplication.note_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.data.NoteItem
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary
import com.drox7.myapplication.utils.Routes


@Composable
fun UiNoteItem(
    item: NoteItem,
    onEvent: (NoteListEvent) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 5.dp, end = 0.dp)
            .clickable {
                onEvent(NoteListEvent.OnItemClick(
                    Routes.NEW_NOTE + "/${item.id}"
                ))
            }
    ) {
        Column(Modifier
            .fillMaxWidth()
            .background(colorScheme.onPrimary)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    color = colorScheme.onBackground,
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 10.dp
                        )
                        .weight(1f),
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        end = 10.dp
                    ),
                    text = item.time,
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
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = md_theme_light_tertiary
                )
                IconButton(
                    onClick = {
                        onEvent(NoteListEvent.OnShowDeleteDiaolog(item))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = Red
                    )
                }
            }
        }
    }
}