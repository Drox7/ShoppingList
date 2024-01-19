package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_table")
data class ShoppingListItem(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val time: String,
    val allItemCount: Int,
    val allSelectedItemCount: Int,
    @ColumnInfo(defaultValue = "0.00") //Default value for migration
    val planSum: Float = 0.00f,
    @ColumnInfo(defaultValue = "0.00")
    val actualSum: Float =0.00f ,
    @ColumnInfo(defaultValue = "0")
    val categoryId: Int =0,
    @ColumnInfo(defaultValue = "0")
    val description: String ="",
    //val planQuantity: Float,
    //val actualQuantity: Float,
)
