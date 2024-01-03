package com.drox7.myapplication.settings_screen

import androidx.compose.ui.graphics.Color
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.ui.theme.GrayLight
import com.drox7.myapplication.ui.theme.GreenLight
import com.drox7.myapplication.ui.theme.Red
import com.drox7.myapplication.ui.theme.YellowLight

object ColorUtils {
    val colorList = listOf(
        "#FF6200EE",
        "#14902B",
        "#FF03DAC5",
        "#FF018786",
        "#FF3699E7",
        "#FFFFB4AB",
        "#FFFBC02D",
        "#FFFFA000",
        "#FF9575CD"
    )

    fun getProgressColor(progress: Float):Color {
        return when(progress){
            in 0.0..0.249 -> GrayLight
            in 0.25..0.499 -> Red
            in 0.5..0.749 -> YellowLight
            in 0.75..0.999 -> BlueLight
            //in 0.67..1.0 -> GreenLight
            in 1.0..1.1 -> GreenLight
            else -> GrayLight
        }
    }
}