package com.drox7.myapplication.shopping_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.utils.DialogController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    private val list = repository.getAllItem()

    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf("")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(false)
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

            }

            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editTableText.value = listItem?.name ?:""
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

    fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                if(showEditTableText.value){
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

}