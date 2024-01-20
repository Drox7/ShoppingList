package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AccountItem_table")
data class AccountItem(
    @PrimaryKey
    val id:Int? = null,
    val isDebit: Boolean,
    val localName: String,
    val internationalName: String,
    @ColumnInfo(defaultValue = "") val description: String ="",
)
