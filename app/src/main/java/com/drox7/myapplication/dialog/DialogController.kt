package com.drox7.myapplication.dialog

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuStateCategory
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuStateUnit

interface DialogController {
    val dialogTitle: MutableState<String>
    val editTableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditTableText: MutableState<Boolean>
    val showEditSumText: MutableState<Boolean>
    val titleColor: MutableState<String>
    val planSumTextFieldValue: MutableState<TextFieldValue>
    val actualSumTextFieldValue: MutableState<TextFieldValue>
    val quantity: MutableState<TextFieldValue>
    val dateTimeItemMillis: MutableState<Long>
    val dropDownMenuStateCategory: MutableState<DropDownMenuStateCategory>
    val dropDownMenuStateUnit: MutableState<DropDownMenuStateUnit>
    fun onDialogEvent(event: DialogEvent)
}