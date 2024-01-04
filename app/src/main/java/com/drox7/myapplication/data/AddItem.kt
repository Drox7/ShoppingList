package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_item_table")
data class AddItem(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val isCheck: Boolean,
    val listId: Int,
    @ColumnInfo(defaultValue = "0.00") //Default value for migration
    val planSum: Float = 0.00f,
    @ColumnInfo(defaultValue = "0.00")
    val actualSum: Float =0.00f ,
)
