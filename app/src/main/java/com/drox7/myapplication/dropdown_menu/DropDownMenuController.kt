package com.drox7.myapplication.dropdown_menu

import androidx.compose.runtime.MutableState

interface DropDownMenuController {
    val titleColor: MutableState<String>
    val expandedMenu: MutableState<Boolean>
    val selectedText : MutableState<String>
    fun onClickItemMenu()
}