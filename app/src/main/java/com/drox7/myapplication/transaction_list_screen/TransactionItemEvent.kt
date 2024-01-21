package com.drox7.myapplication.transaction_list_screen

import com.drox7.myapplication.data.TransactionItem

sealed class TransactionItemEvent {
    data class OnDelete(val item: TransactionItem): TransactionItemEvent()
    data class OnShowEditDialog(val item: TransactionItem): TransactionItemEvent()
    data class OnTextChange(val text: String): TransactionItemEvent()
    data class OnCheckedChange(val item: TransactionItem): TransactionItemEvent()
    object OnItemSave: TransactionItemEvent()
}