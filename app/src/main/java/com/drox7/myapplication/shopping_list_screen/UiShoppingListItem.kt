package com.drox7.myapplication.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.drox7.myapplication.R
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.GreenLight
import com.drox7.myapplication.utils.Routes


@Composable
fun UiShoppingListItem(
    item: ShoppingListItem,
    onEvent: (ShoppingListEvent) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            //.background(color = GrayLight)
            .padding(
            start = 0.dp, top = 5.dp, end = 0.dp
        )
    ) {
        val (card, deleteButton, editButton, counter, sum, col) = createRefs()
        Card(
           // elevation = 0.dp,
            modifier = Modifier
                //.background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                .clickable {
                    onEvent(ShoppingListEvent.OnItemClick(
                        Routes.ADD_ITEM + "/${item.id}"
                    ))
                },
            shape = RoundedCornerShape(15.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(colorScheme.onPrimary)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(col) {}
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                       // color = DarkText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = item.time,
                    style = TextStyle(
                      //  color = LightText,
                        fontSize = 12.sp
                    )
                )
//                Card(
//                    //sum
//                    shape = RoundedCornerShape(5.dp),
//                    modifier = Modifier
////                        .constrainAs(sum) {
////                            top.linkTo(counter.bottom)
////                            bottom.linkTo(counter.bottom)
////                            end.linkTo(card.end)
////                        }
//                        .padding(top = 5.dp),
//
//
//                    ) {
                Text(
                    text = "2000.98 р./10000.45 р.",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    ),
                    modifier = Modifier,
                    // .background(Red)
//                            .padding(
//                                top = 2.dp,
//                                bottom = 2.dp,
//                                start = 5.dp,
//                                end = 5.dp
                    //     ),
                    color = BlueLight
                )
                //  }

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp),
                    progress = 0.5f,
                    color = BlueLight

                )
            }
        }
        IconButton( //deleteButton
            onClick = {
                onEvent(ShoppingListEvent.OnShowDeleteDialog(item))
            },
            modifier = Modifier
                .constrainAs(deleteButton) {
                    top.linkTo(col.top)
                    bottom.linkTo(col.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 10.dp, top = 10.dp)
                .size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete",
                modifier = Modifier
                    .clip(CircleShape)
                    // .background(Red)
                    .padding(
                        6.dp
                    ),
                tint = colorScheme.error
            )
        }
        Card( //counter
            shape = RoundedCornerShape( 0.dp),
            elevation = 0.dp,
            modifier = Modifier
                .constrainAs(counter) {
                    top.linkTo(col.top)
                    bottom.linkTo(col.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 0.dp, top = 10.dp)
        ) {
            Text(
                text = "${item.allSelectedItemCount}/${item.allItemCount}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .background(colorScheme.onPrimary)
                    .padding(
                        top = 7.dp,
                        bottom = 7.dp,
                        start = 4.dp,
                        end = 7.dp
                    ),
                color = BlueLight
            )
        }


        IconButton( //EditButton
            onClick = {
                      onEvent(ShoppingListEvent.OnShowEditDialog(item))
            },
            modifier = Modifier
                .constrainAs(editButton) {
                    top.linkTo(col.top)
                    bottom.linkTo(col.bottom)
                    end.linkTo(counter.start)
                }
                .padding(end = 10.dp, top = 10.dp)
                .size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = "Edit",
                modifier = Modifier
                    //.clip(CircleShape)
                    //.background(GreenLight)
                    .padding(
                        6.dp
                    ),
                tint = GreenLight
            )
        }

    }
}