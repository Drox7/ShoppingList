package com.drox7.myapplication.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<String>
    val editTableText: MutableState<String>
    val editPlanSumText: MutableState<String>
    val editActualSumText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditTableText: MutableState<Boolean>
    val showEditSumText: MutableState<Boolean>
    val titleColor: MutableState<String>
    fun onDialogEvent(event: DialogEvent)
}