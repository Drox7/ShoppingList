package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "accountBalances_item_table")
data class AccountBalancesItem(
    @PrimaryKey
    val id:Int? = null,
    val dateTime: Date?,
    val nameAccount: String,
    @ColumnInfo(defaultValue = "0") val typeAccountId: Int = 0, //Счет, Дебитовая карта, Кредитная карта, Долг Физического лица, Акции, Облигации
    @ColumnInfo(defaultValue = "0") val assetLiabilityId: Int = 0, // Банк, Дядя Вася, Брокер,
    @ColumnInfo(defaultValue = "0") val currencyAccountId: Int = 0,
    @ColumnInfo(defaultValue = "0.00") val balanceSum: Float =0.00f,
    @ColumnInfo(defaultValue = "0.00") val balanceQuantity: Float = 0.000f,
    @ColumnInfo(defaultValue = "") val description: String =""
)
