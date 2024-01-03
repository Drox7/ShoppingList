package com.drox7.myapplication.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.main_screen.UiTopBar
import com.drox7.myapplication.ui.theme.GrayLight


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsListScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val list = viewModel.colorItemListState.value
    Scaffold(
        topBar = {
            UiTopBar(
                titleColor = GrayLight,
                onClick = {onPopBackStack()},
                titleText = "Настройки",
                iconVector = Icons.Filled.ArrowBack
            )
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Title color",
                fontSize = 16.sp
            )
            Text(
                text = "Select a title color:",
                fontSize = 12.sp
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                items(list) { item ->
                    UiColorItem(item) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}