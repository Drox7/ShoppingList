package com.drox7.myapplication.add_item_screen

import com.drox7.myapplication.data.AddItem

sealed class AddItemEvent {
    data class OnDelete(val item: AddItem): AddItemEvent()
    data class OnShowEditDialog(val item: AddItem): AddItemEvent()
    data class OnTextChange(val text: String): AddItemEvent()
    data class OnCheckedChange(val item: AddItem): AddItemEvent()
    data class OnDescriptionChange(val description: String) : AddItemEvent()
    object OnItemSave: AddItemEvent()
}