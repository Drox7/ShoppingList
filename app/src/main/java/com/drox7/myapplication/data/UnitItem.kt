package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit_item_table")
data class UnitItem(
    @PrimaryKey
    val id:Int? = null,
    val localName: String,
    val internationalName: String,
    @ColumnInfo(defaultValue = "1.00000") val coefficientToBase: Float =1.00000f ,
    @ColumnInfo(defaultValue = "piece") val typeUnit: String = "piece", // штучный товар, обемый , весовой, времнной,
    val isBase: Boolean,
)
