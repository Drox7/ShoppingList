package com.drox7.myapplication.add_item_screen

import com.drox7.myapplication.data.AddItem

sealed class AddItemEvent {
    data class onDelete(val item: AddItem): AddItemEvent()
    data class onShowEditDialog(val item: AddItem): AddItemEvent()
    data class onTextChange(val text: String): AddItemEvent()
    data class OnCheckedChange(val item: AddItem): AddItemEvent()
    object OnItemSave: AddItemEvent()
}