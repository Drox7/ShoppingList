package com.drox7.myapplication.utils

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun ViewModel.getCurrentTime(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val cv = Calendar.getInstance()
    return formatter.format(cv.time)
}