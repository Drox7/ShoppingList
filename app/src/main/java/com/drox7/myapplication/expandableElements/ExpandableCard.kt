package com.drox7.myapplication.expandableElements

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    expandableCardController: ExpandableCardController,
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    summarySum: MutableState<Float> = mutableFloatStateOf(0f),
    summarySumToday: MutableState<Float> = mutableFloatStateOf(0f),
    summarySumMonth: MutableState<Float> = mutableFloatStateOf(0f),
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 15,
    shape: Shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
    bottomPadding: Dp = 55.dp,
    padding : Dp = 5.dp,
    showSummary: Boolean = false,
    showDescription: Boolean = false
) {
    val titleColor = Color(android.graphics.Color.parseColor(expandableCardController.titleColor.value))
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .alpha(1f)
            .padding(end = 0.dp, start = 0.dp, bottom = bottomPadding, top = 0.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .background(colorScheme.background)
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(6f),
                   // text = expandableCardController.description.value,
                    text = title+" "+summarySum.value.toString()+"₽",
                    fontSize = titleFontSize,
                    //fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
            if (expandedState && showSummary) {
                Row () {
                    Column(
                        modifier = Modifier
                            .weight(1.2f)
                            .padding(padding)

                    ) {
                        Text(modifier = Modifier
                            .alpha(1f),
                            text = "Сегодня:", color = titleColor)
                        Text(text = "Текущий месяц:", color = titleColor)
                        Text(text = "Предыдущий месяц:", color = titleColor)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(padding)
                    ) {
                        Text(text = "${summarySumToday.value}₽")
                        Text(text = "${summarySumMonth.value}₽")
                        Text(text = "5 000 000 р. ")
                    }

                }
            }

            if (expandedState && showDescription) {
                TextField(
                    value = expandableCardController.description.value,
                    onValueChange = {
                        expandableCardController.onCardEvent(ExpandableCardEvent.OnTextChange(it))
                    },
                    textStyle = TextStyle(
                        color = colorScheme.onBackground
                    ),
                    maxLines = descriptionMaxLines,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = colorScheme.onBackground
                    ),
                    placeholder = { Text("Description...", color = md_theme_light_tertiary) },
//                    value = viewModel.description,
//                   overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}