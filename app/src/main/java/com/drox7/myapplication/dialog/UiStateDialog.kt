package com.drox7.myapplication.dialog

import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.UnitItem

data class UiStateDialog(
    val unitItem: UnitItem?,
    val categoryItem: CategoryItem?,
    val transactionItem: TransactionItem?,
)
