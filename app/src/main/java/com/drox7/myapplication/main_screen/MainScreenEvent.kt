package com.drox7.myapplication.main_screen

import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.shopping_list_screen.ShoppingListEvent

sealed class MainScreenEvent {
    object OnShowEditDialog : MainScreenEvent()
    object OnItemSave : MainScreenEvent()
}
