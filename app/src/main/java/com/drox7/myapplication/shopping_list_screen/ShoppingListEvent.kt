package com.drox7.myapplication.shopping_list_screen

import com.drox7.myapplication.data.ShoppingListItem

sealed class ShoppingListEvent {
    data class OnShowDeleteDialog(val item: ShoppingListItem) : ShoppingListEvent()
    data class OnShowEditDialog(val item: ShoppingListItem) : ShoppingListEvent()
    data class OnItemClick(val route: String) : ShoppingListEvent()
    object OnItemSave : ShoppingListEvent()

}
