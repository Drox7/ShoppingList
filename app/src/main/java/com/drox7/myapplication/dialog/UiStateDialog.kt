package com.drox7.myapplication.dialog

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.UnitItem

data class UiStateDialog(
    val quantity :MutableState<TextFieldValue>,
    val unitItem: UnitItem?,
    val categoryItem: CategoryItem?,
    val transactionItem: TransactionItem?,
)
