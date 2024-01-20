package com.drox7.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assetLiability_table")
data class AssetLiabilityItem(
    @PrimaryKey
    val id:Int? = null,
    val isAsset: Boolean,
    val localName: String,
    val internationalName: String,
)
