package com.drox7.myapplication.utils

import com.drox7.myapplication.data.ShoppingListItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val cv = Calendar.getInstance()
    return formatter.format(cv.time)
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