package com.drox7.myapplication.add_item_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    Text(
        text = "ADD Item Screen",
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth()
            .wrapContentHeight()
    )
}