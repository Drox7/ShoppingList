package com.drox7.myapplication.dialog

import androidx.compose.ui.text.input.TextFieldValue

sealed class DialogEvent {
    data class OnTextChange(val text: String) : DialogEvent()
    data class OnPlanSumChange(val textFieldValue: TextFieldValue) : DialogEvent()
    data class OnActualSumChange(val textFieldValue: TextFieldValue) : DialogEvent()
    data class OnQuantityChange(val textFieldValue: TextFieldValue) : DialogEvent()
    object OnCancel : DialogEvent()
    object OnConfirm : DialogEvent()
}
