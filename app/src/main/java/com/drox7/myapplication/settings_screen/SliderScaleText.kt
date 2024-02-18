package com.drox7.myapplication.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.utils.getScaleText

@Composable
fun SliderScaleText(
    titleColor: Color,
    scaleTextValue: Float,
    onEvent:(SettingsEvent) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(scaleTextValue) }
    Column {
        Text(
            modifier = Modifier.padding(start = 10.dp, top = 0.dp),
            text = "Размер текста",
            color = titleColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp.times(getScaleText(sliderPosition))
        )
        Slider(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onEvent(SettingsEvent.OnScaleTextChange(it))
            }
        )
//        Text(
//            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
//            text = sliderPosition.toString()
//        )
    }
}