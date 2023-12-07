package com.drox7.myapplication.shopping_list_screen

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.DarkText
import com.drox7.myapplication.ui.theme.GreenLight
import com.drox7.myapplication.ui.theme.LightText
import com.drox7.myapplication.ui.theme.Red


@Preview(showBackground = true)
@Composable
fun UiShoppingListItem() {
    ConstraintLayout(
        modifier = Modifier.padding(
            start = 3.dp, top = 18.dp, end = 3.dp
        )
    ) {
        val (card, deleteButton, editButton, counter, sum) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Пятак",
                    style = TextStyle(
                        color = DarkText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "12/12/2023 13:00",
                    style = TextStyle(
                        color = LightText,
                        fontSize = 12.sp
                    )
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp)

                )
            }
        }
        IconButton( //deleteButton
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(deleteButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(card.end)
                }
                .padding(end = 10.dp, top = 10.dp)
                .size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Red)
                    .padding(
                        6.dp
                    ),
                tint = Color.White
            )
        }
        IconButton( //EditButton
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(editButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 10.dp, top = 10.dp)
                .size(35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = "Edit",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(GreenLight)
                    .padding(
                        6.dp
                    ),
                tint = Color.White
            )
        }
        Card( //counter
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .constrainAs(counter) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(editButton.start)
                }
                .padding(end = 5.dp, top = 10.dp)
        ) {
            Text(
                text = "15/5",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                   // .background(Red)
                    .padding(
                        top = 7.dp,
                        bottom = 7.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),
                color = BlueLight
            )
        }
        Card( //sum
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .constrainAs(sum) {
                    top.linkTo(card.bottom)
                    bottom.linkTo(card.bottom)
                    end.linkTo(card.end)
                }
                .padding(end = 9.dp, top = 10.dp)
        ) {
            Text(
                text = "2000.98 р./10000.45 р.",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                ),
                modifier = Modifier
                   // .background(Red)
                    .padding(
                        top = 2.dp,
                        bottom = 3.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),
                color = BlueLight
            )
        }
    }
}