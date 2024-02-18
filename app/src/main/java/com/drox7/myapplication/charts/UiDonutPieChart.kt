package com.drox7.myapplication.charts

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.drox7.myapplication.data.SummaryItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UiDonutPieChart(
    summaryList: List<SummaryItem>
) {
    var sumTextSlice by remember { mutableStateOf("") }
    val context = LocalContext.current
    val donutChartData = PieChartData(
//        slices = listOf(
//            PieChartData.Slice(summaryList[0].category, summaryList[0].sum, Color(0xFF870A27)),
//            PieChartData.Slice(summaryList[1].category, summaryList[1].sum, Color(0xFF5F0A87)),
//            PieChartData.Slice(summaryList[2].category, summaryList[2].sum, Color(0xFF20BF55)),
//            PieChartData.Slice(summaryList[3].category, summaryList[3].sum, Color(0xFFEC9F05)),
//            PieChartData.Slice(summaryList[4].category, summaryList[4].sum, Color(0xFFF53844)),
//            PieChartData.Slice(summaryList[5].category, summaryList[5].sum, Color(0xFF9575CD)),
//            PieChartData.Slice(summaryList[6].category, summaryList[6].sum, Color(0xFF7598CD)),
//            PieChartData.Slice(summaryList[7].category, summaryList[7].sum, Color(0xFFDCE775)),
//            // PieChartData.Slice(summaryList[8].category, summaryList[8].sum, Color(0xFFFF8A65)),
//            // PieChartData.Slice(summaryList[9].category, summaryList[9].sum, Color(0xFF81C784)),
//            //PieChartData.Slice(summaryList[10].category, summaryList[10].sum, Color(0xFFC781B8)),
//            // PieChartData.Slice(summaryList[11].category, summaryList[11].sum, Color(0xFF9E81C7)),
//        ),
        slices = summaryList.map{
            PieChartData.Slice(it.category,it.sum, Color(0xFF20BF55*it.sum.toInt()))
        },
        plotType = PlotType.Donut
    )

    val donutChartConfig = PieChartConfig(
        labelVisible = true,
        strokeWidth = 120f,
        labelColor = MaterialTheme.colorScheme.onBackground,
        activeSliceAlpha = .9f,
        isEllipsizeEnabled = true,
        labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
        isAnimationEnable = true,
        chartPadding = 30,
        labelFontSize = 30.sp,
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        isSumVisible = true,
        showSliceLabels = true,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(900.dp)
    ) {

        DonutPieChart(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .height(350.dp),

            donutChartData,
            donutChartConfig
        ) { slice ->
            //Toast.makeText(context, slice.value.toString(), Toast.LENGTH_SHORT).show() 
        sumTextSlice = "${slice.label}: ${String.format("%.2f",slice.value)} ₽"
        }
        Text(
            text = sumTextSlice,

            modifier = Modifier
                //.padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Legends(
            legendsConfig = DataUtils.getLegendsConfigFromPieChartData(
                pieChartData = donutChartData, 2
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        )
    }
}