package com.drox7.myapplication.main_screen

import com.drox7.myapplication.R

sealed class BottomNavItem (val title: String, val iconId: Int, val route: String ){
    object ListItem: BottomNavItem("list", R.drawable.list_icon, "route1")
    object NoteItem: BottomNavItem("Note", R.drawable.note_icon, "route2")
    object AboutItem: BottomNavItem("About", R.drawable.about_icon, "route3")
    object SettingsItem: BottomNavItem("Settings", R.drawable.settings_icon, "route4")
}
