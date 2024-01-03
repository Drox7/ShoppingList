package com.drox7.myapplication.main_screen

import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun UiTopBar(
    titleColor: Color,
    onClick: () -> Unit,
    titleText: String,
    iconVector: ImageVector,
) {
    TopAppBar(
        title = {
            Text(text = titleText)
        },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(
                    iconVector,
                    iconVector.name,
                    tint = titleColor
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = Color.White,
        //elevation = 10.dp
    )
}