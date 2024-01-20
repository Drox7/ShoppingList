package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_item_table")
data class CurrencyItem(
    @PrimaryKey
    val id:Int? = null,
    val symbol: String,
    val isLocal: Boolean,
    val localName: String,
    val internationalName: String,
    @ColumnInfo(defaultValue = "0.00000") val currentRateToDollar: Float =0.00000f ,


)
