package com.drox7.myapplication.dialog

sealed class DialogEvent {
    data class OnTextChange(val text: String) : DialogEvent()
    object OnCancel : DialogEvent()
    object OnConfirm : DialogEvent()
}
