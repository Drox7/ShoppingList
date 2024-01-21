package com.drox7.myapplication.add_item_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.R
import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.Typography
import com.drox7.myapplication.utils.getCurrentTimeStamp

@Composable

fun UiAdItem(
    titleColor: Color,
    item: AddItem,
    onEvent:(AddItemEvent) -> Unit

) {
    val minPaddingText = dimensionResource(R.dimen.padding_minimum_text)
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 1.dp, start = 0.dp, end = 0.dp)
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
                        .padding(start = 5.dp)
                    ,
                    text = item.name,
                    style = Typography.bodyLarge,
                    color = titleColor
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "${item.planSum} р./${item.actualSum} р.",
                    style = TextStyle(
                        //fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    ),
                    )
            }

            IconButton(
                onClick = {
                    if (item.actualSum != 0f) {
                        onEvent(
                            AddItemEvent.OnAddToTransactionList(
                                TransactionItem(
                                    null,
                                    getCurrentTimeStamp(), item.name, true, sum = item.actualSum
                                )
                            )
                        )
                        Toast.makeText(
                            context,
                            "${item.name} - добавлен в расход",
                            Toast.LENGTH_LONG
                        ).show()
                    } else  Toast.makeText(
                        context,
                        "${item.name} - не заполнена сумма факт",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                Icon(
                    modifier = Modifier.alpha(0.7f),
                  //  painter = painterResource(id = R.drawable.baseline_difference_icon),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription ="Save to Transaction list",
                    tint = titleColor
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