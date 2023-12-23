package com.drox7.myapplication.add_item_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drox7.myapplication.R
import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.Typography

@Composable

fun UiAdItem(
    item: AddItem,
    onEvent:(AddItemEvent) -> Unit
) {
    val minPaddingText = dimensionResource(R.dimen.padding_minimum_text)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)
            .clickable {
                onEvent(AddItemEvent.OnShowEditDialog(item))
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text (
                modifier = Modifier
                    .weight(1f)
                    .padding(minPaddingText)
                ,
                text = item.name,
               style = Typography.bodyLarge
            )

            Checkbox(
                checked = item.isCheck ,
                onCheckedChange = {isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(isCheck = isChecked)))
                }
            )
            IconButton(
                onClick = {
                    onEvent(AddItemEvent.OnDelete(item))
                 }
            ) {
                Icon(painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription ="Delete",
                    tint = Red
                )
                
            }
        }
    }
}