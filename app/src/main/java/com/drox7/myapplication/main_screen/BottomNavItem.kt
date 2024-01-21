package com.drox7.myapplication.main_screen

import com.drox7.myapplication.R
import com.drox7.myapplication.utils.Routes

sealed class BottomNavItem (val title: String, val iconId: Int, val route: String ){
//    object ListItem: BottomNavItem("list", R.drawable.list_icon, Routes.SHOPPING_LIST)
    object ListItem: BottomNavItem("План", R.drawable.list_icon, Routes.SHOPPING_LIST)
    object TransactionListItem: BottomNavItem("Расходы", R.drawable.currency_exchange_icon, Routes.TRANSACTION_LIST)
//    object NoteItem: BottomNavItem("Note", R.drawable.note_icon, Routes.NOTE_LIST)
    object NoteItem: BottomNavItem("Заметки", R.drawable.note_icon, Routes.NOTE_LIST)
//    object AboutItem: BottomNavItem("About", R.drawable.about_icon, Routes.ABOUT)
    object AboutItem: BottomNavItem("О прило..", R.drawable.about_icon, Routes.ABOUT)
//    object SettingsItem: BottomNavItem("Settings", R.drawable.settings_icon, Routes.SETTINGS)
    object SettingsItem: BottomNavItem("Настройки", R.drawable.settings_icon, Routes.SETTINGS)
}
