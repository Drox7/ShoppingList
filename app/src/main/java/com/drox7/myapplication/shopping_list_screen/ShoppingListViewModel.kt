package com.drox7.myapplication.shopping_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    private val list = repository.getAllItem()

    private val _uiEvent = Channel<UiEvent>() // transmitter for Broadcasting Events to View model and Compose
    val uEvent = _uiEvent.receiveAsFlow()     //receiver

    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(true)
        private set
    override var showEditTableText = mutableStateOf(true)
        private set

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnItemSave -> {
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editTableText.value,
                            "12-12/2023 13:00",
                            listItem?.allItemCount ?: 0,
                            listItem?.allSelectedItemCount ?: 0
                        )
                    )
                }
            }

            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editTableText.value = listItem?.name ?: ""
                dialogTitle.value = "List name"
                showEditTableText.value = true

            }

            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Delete this item?"
                showEditTableText.value = false
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
                    onEvent(ShoppingListEvent.OnItemSave)
                } else {
                    viewModelScope.launch {
                        listItem?.let { repository.deleteItem(it) }
                    }
                }
                openDialog.value = false
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
    viewModelScope.launch {
        _uiEvent.send(event)
    }
    }
}