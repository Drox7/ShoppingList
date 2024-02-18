package com.drox7.myapplication.expandableElements

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import com.drox7.myapplication.charts.UiDonutPieChart
import com.drox7.myapplication.data.SummaryItem
import com.drox7.myapplication.di.AppModule.MainColor
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    expandableCardController: ExpandableCardController,
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    summarySum: MutableState<Float> = mutableFloatStateOf(0f),
    summarySumToday :Float = 0f,
    //summarySumToday: MutableState<Float> = mutableFloatStateOf(0f),
    summaryItemList: List<SummaryItem> = emptyList(),
    summaryMonthMap: Map<String,Float> = emptyMap(),
    titleFontWeight: FontWeight = FontWeight.Bold,
    descriptionFontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 15,
    shape: Shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
    bottomPadding: Dp = 55.dp,
    padding: Dp = 5.dp,
    showSummary: Boolean = false,
    showDescription: Boolean = false
) {
    val titleColor =
        Color(android.graphics.Color.parseColor(MainColor))
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )
    val summaryText: String = if (summarySum.value != 0.0f) {
        String.format("%.2f",summarySum.value) + " ₽"
    } else ""


    Card(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 4.dp
//        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .alpha(1f)
            .padding(end = 0.dp, start = 0.dp, bottom = bottomPadding, top = 0.dp)
            .animateContentSize(
                animationSpec = spring(3f)
//                animationSpec = tween(
//                    durationMillis = 200,
//                    easing = LinearEasing
//                )
            )
           ,
        shape = shape,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .background(colorScheme.onPrimary)
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
                    text = "$title    $summaryText",
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
            if (expandedState && showSummary) {
                Column(modifier = Modifier) {
                    Row() {
                        Column(
                            modifier = Modifier
                                .weight(1.2f)
                                .padding(padding)

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 7.dp)
                                    .alpha(1f),
                                text = "Сегодня:", color = titleColor,
                                fontWeight = titleFontWeight,
                            )
                            summaryMonthMap.forEach{
                                Text(
                                    text = "${getMonthName(it.key)}:", modifier = Modifier
                                        .padding(bottom = 7.dp), color = titleColor,
                                    fontWeight = titleFontWeight,
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(padding)
                        ) {
                            Text(
                                text = "${String.format("%.2f",summarySumToday)} ₽", modifier = Modifier
                                    .padding(bottom = 7.dp),
                                fontWeight = titleFontWeight,
                            )

                            summaryMonthMap.forEach{
                                Text(
                                    text = "${String.format("%.2f", it.value)} ₽", modifier = Modifier
                                        .padding(bottom = 7.dp),
                                    fontWeight = titleFontWeight,
                                )
                            }
                        }

                    }
                    UiDonutPieChart(summaryItemList)
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

fun getMonthName(date:String) :String {
    if(date.startsWith("01")) return "Январь"
    else if(date.startsWith("02")) return "Февраль"
    else if(date.startsWith("03")) return "Март"
    else if(date.startsWith("04")) return "Апрель"
    else if(date.startsWith("05")) return "Май"
    else if(date.startsWith("06")) return "Июнь"
    else if(date.startsWith("07")) return "Июль"
    else if(date.startsWith("08")) return "Август"
    else if(date.startsWith("09")) return "Сентябрь"
    else if(date.startsWith("10")) return "Октябрь"
    else if(date.startsWith("11")) return "Ноябрь"
    else if(date.startsWith("12")) return "Декабрь"
    return ""
}

//@Composable
//fun TextSummary(Text: String){
//    Text(
//        text = "$Text ₽", modifier = Modifier
//            .padding(bottom = 7.dp)
//    )
//}