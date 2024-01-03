package com.drox7.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_item_table")
data class AddItem(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val isCheck: Boolean,
    val listId: Int
)
