package com.drox7.myapplication.main_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.shopping_list_screen.ShoppingListEvent
import com.drox7.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {
    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnItemSave -> {
                if (editTableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editTableText.value,
                            "12-12/2023 13:00",
                            0,
                            0
                        )
                    )
                }
            }

            is MainScreenEvent.OnShowEditDialog -> {
                openDialog.value = true
            }


        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                if (showEditTableText.value) {
                    onEvent(MainScreenEvent.OnItemSave)
                    editTableText.value = ""
                }
                openDialog.value = false
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }
        }
    }
}