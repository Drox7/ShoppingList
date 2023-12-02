package com.drox7.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_name")
data class ShoppingListItem(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val time: String,
    val allItemCount: String,
    val allSelectedItemCount: String
)
