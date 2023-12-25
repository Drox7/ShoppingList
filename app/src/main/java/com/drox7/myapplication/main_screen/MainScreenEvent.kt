package com.drox7.myapplication.main_screen

sealed class MainScreenEvent {
    object OnShowEditDialog : MainScreenEvent()
    object OnItemSave : MainScreenEvent()
    data class Navigate(val route: String): MainScreenEvent()
    data class NavigateMain(val route: String): MainScreenEvent()
}
