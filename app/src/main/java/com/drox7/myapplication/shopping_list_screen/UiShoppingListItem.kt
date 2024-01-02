package com.drox7.myapplication.shopping_list_screen


import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.drox7.myapplication.R
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.settings_screen.ColorUtils
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.utils.ProgressHelper
import com.drox7.myapplication.utils.Routes


@Composable
fun UiShoppingListItem(
    titleColor: Color,
    item: ShoppingListItem,
    onEvent: (ShoppingListEvent) -> Unit
) {
    val progress = ProgressHelper.getProgress( item.allItemCount, item.allSelectedItemCount)
    ConstraintLayout(
        modifier = Modifier
            .padding(
                start = 0.dp, top = 5.dp, end = 0.dp
            )

    ) {
        val (card, deleteButton, editButton, counter, sum, col) = createRefs()
        Card(
            // elevation = 0.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onEvent(
                        ShoppingListEvent.OnItemClick(
                            Routes.ADD_ITEM + "/${item.id}"
                        )
                    )
                },
            //shape = CutCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
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
                        //fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = titleColor
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = item.time,
                    style = TextStyle(
                        //  color = LightText,
                        fontSize = 10.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
//                Text(
//                    text = "2000.98 р./10000.45 р.",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 13.sp,
//                    ),
//                    modifier = Modifier,
//
//                    )


//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 7.dp),
//                    progress = ProgressHelper.getProgress(
//                        item.allItemCount,
//                        item.allSelectedItemCount
//                    ),
//                    color = titleColor
//
//                )
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
                //painter = painterResource(id = R.drawable.delete_icon),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .clip(CircleShape)
                    // .background(Red)
                    .padding(
                        6.dp
                    ),
                tint = Red
            )
        }
        Card( //counter
            shape = RoundedCornerShape(0.dp),
            elevation = 0.dp,
            modifier = Modifier
                .constrainAs(counter) {
                    top.linkTo(col.top)
                    bottom.linkTo(col.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 0.dp, top = 10.dp)
                .background(colorScheme.onPrimary)
        ) {
            Box(contentAlignment = Alignment.Center) {
                var fontSizeCounter = 12.sp
//                CircularProgressAnimated(
//                    progress = ProgressHelper.getProgress(
//                        item.allItemCount,
//                        item.allSelectedItemCount,
//                    ),
//                    titleColor = titleColor
//                )
                CircularProgressIndicator(
                    modifier = Modifier.background(colorScheme.onPrimary),
                    progress = progress,
                    strokeWidth = 3.dp,
                    color = ColorUtils.getProgressColor(progress)
                )
                if (item.allItemCount > 99) fontSizeCounter = 9.sp
                Text(
                    text = "${item.allSelectedItemCount}/${item.allItemCount}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSizeCounter,
                        color = ColorUtils.getProgressColor(progress)
                    ),
                    modifier = Modifier
                        .background(Color.Transparent),
//                    .padding(
//                        top = 12.dp,
//                        bottom = 7.dp,
//                        start = 7.dp,
//                        end = 7.dp
//                    ),
                    //textAlign = TextAlign.Center

                )
            }
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
                    .padding(
                        6.dp
                    ),
                tint = titleColor
            )
        }

    }

}

@Composable
fun CircularProgressAnimated(progress: Float, titleColor: Color) {
    //val progressValue = 0.75f
    val infiniteTransition = rememberInfiniteTransition(label = "test")
    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progress,
        animationSpec = infiniteRepeatable(
            animation = tween(4000),
            //repeatMode = RepeatMode.Restart,
        ),
        label = "1111"
    )

    CircularProgressIndicator(
        progress = progressAnimationValue,
        strokeWidth = 3.dp,
        color = titleColor,
        modifier = Modifier.background(colorScheme.onPrimary)
    )
}