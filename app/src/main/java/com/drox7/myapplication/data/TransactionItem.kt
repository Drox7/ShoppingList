package com.drox7.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_item_table")
data class TransactionItem(
    @PrimaryKey
    val id:Int? = null,
    val dateTime: Date?,
    val name: String,
    val isExpense: Boolean,
    @ColumnInfo(defaultValue = "0")val categoryId: Int = 0,
    @ColumnInfo(defaultValue = "Expense") val typeTransaction: String ="Expense",
    @ColumnInfo(defaultValue = "0") val typeAccountId: Int = 0, //Счет, Дебитовая карта, Кредитная карта, Долг Физического лица, Акции, Облигации
    @ColumnInfo(defaultValue = "0") val assetLiabilityId: Int = 0, // Банк, Дядя Вася, Брокер,
    @ColumnInfo(defaultValue = "0") val currencyId: Int = 0,
    @ColumnInfo(defaultValue = "0") val unitId: Int = 0, // 0 -  штука, 1 - кг, 2 - л
    @ColumnInfo(defaultValue = "0.00") val quantity: Float = 0.000f,
    @ColumnInfo(defaultValue = "0.00") val price: Float =0.00f ,
    @ColumnInfo(defaultValue = "0.00") val sum: Float =0.00f ,

    @ColumnInfo(defaultValue = "") val description: String =""
)
