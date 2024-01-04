package com.drox7.myapplication.add_item_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.R
import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.Typography

@Composable

fun UiAdItem(
    titleColor: Color,
    item: AddItem,
    onEvent:(AddItemEvent) -> Unit
) {
    val minPaddingText = dimensionResource(R.dimen.padding_minimum_text)

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp)
            .clickable {
                onEvent(AddItemEvent.OnShowEditDialog(item))
            },
    ) {

        Row(
            modifier = Modifier
                .background(colorScheme.onPrimary)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text (
                    modifier = Modifier
                       // .weight(1f)
                        .padding(minPaddingText)
                    ,
                    text = item.name,
                    style = Typography.bodyLarge,
                    color = titleColor
                )
                Text(
                    modifier = Modifier.padding(minPaddingText),
                    text = "${item.planSum} р./${item.actualSum} р.",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    ),
                    )
            }




            Checkbox(
                checked = item.isCheck ,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    uncheckedColor = titleColor,
                    checkmarkColor = titleColor
                ),
                onCheckedChange = {isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(isCheck = isChecked)))
                }
            )
            IconButton(
                onClick = {
                    onEvent(AddItemEvent.OnDelete(item))
                 }
            ) {
                Icon(
                    //painter = painterResource(id = R.drawable.delete_icon),
                    imageVector = Icons.Default.Delete,
                    contentDescription ="Delete",
                    tint = Red
                )

            }
        }
    }
}