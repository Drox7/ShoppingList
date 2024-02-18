package com.drox7.myapplication.utils

import com.drox7.myapplication.data.ShoppingListItem
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val cv = Calendar.getInstance().time
    return formatter.format(cv.time)
}
fun formatDateTime(date: Date?):String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(date!!)
}
fun formatDate(date: Date?):String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(date!!)
}
fun formatTime(date: Date?):String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date!!)
}
fun getCurrentTimeStamp(): Timestamp {
    val currentTimeMillis = System.currentTimeMillis()
    return Timestamp(currentTimeMillis)
}

fun getScaleText(initialValue:Float): Float {
    return initialValue + 0.5f
}

fun groupByCategory(
    originList: List<ShoppingListItem>,
    categoryId: Int
):List<ShoppingListItem> {
    val filteredList = if (categoryId != 0) {
        originList.filter {
            it.categoryId == categoryId
        }
    }  else originList

    return filteredList
}

fun sortList(
    originList: List<ShoppingListItem>,
    sortId: Int //0 Sort by date, 1 sort by name, 2 empty, 3 sorted by finished
):List<ShoppingListItem> {
    val filteredList = when(sortId) {
        0 -> {
            originList.sortedByDescending {
                it.id
            }
        }

        1 -> {
            originList.sortedBy {
                it.name
            }
        }

        2 -> {
            originList.sortedBy {
                it.allItemCount
            }
        }

        3 -> {
            originList.sortedBy {
                if (it.allItemCount != 0)
                it.allItemCount - it.allSelectedItemCount else 300
            }
        }

        else  -> originList
    }
    return filteredList
}