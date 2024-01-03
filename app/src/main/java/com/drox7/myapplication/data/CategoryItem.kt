package com.drox7.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryItem(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val description: String,
    val time: String
)
