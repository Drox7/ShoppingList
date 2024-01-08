package com.drox7.myapplication.dropdown_menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun UiDropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            modifier = Modifier.background(colorScheme.onPrimary),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("A-Z") },
                onClick = { Toast.makeText(context, "A-z", Toast.LENGTH_SHORT).show() }
            )
            DropdownMenuItem(
                text = { Text("Date") },
                onClick = { Toast.makeText(context, "Date", Toast.LENGTH_SHORT).show() }
            )
            DropdownMenuItem(
                text = { Text("Manual") },
                onClick = { Toast.makeText(context, "Manual", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}