package com.drox7.myapplication.add_item_screen

import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.data.TransactionItem

sealed class AddItemEvent {
    data class OnDelete(val item: AddItem): AddItemEvent()
    data class OnShowEditDialog(val item: AddItem): AddItemEvent()
    data class OnTextChange(val text: String): AddItemEvent()
    data class OnCheckedChange(val item: AddItem): AddItemEvent()
    data class OnAddToTransactionList(val item: TransactionItem): AddItemEvent()
    object OnItemSave: AddItemEvent()
}