package com.drox7.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item")
data class ShoppingListName(
    @PrimaryKey
    val id:Int,
    val name: String,
    val time: String,
    val allItemCount: String,
    val allSelectedItemCount: String
)
