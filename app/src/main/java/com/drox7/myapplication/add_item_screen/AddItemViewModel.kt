package com.drox7.myapplication.add_item_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.data.AddItemRepository
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel(), DialogController {
    var itemsList: Flow<List<AddItem>>? = null
    init {
        val listId = savedStateHandle.get<String>("listId")?.toInt()
        itemsList = listId?.let { repository.getAllItemById(it) }
        Log.d("MyLog", "List id View model $listId")
    }


    override var dialogTitle = mutableStateOf("")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(true)
        private set

    fun onEvent(){

    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editTableText.value = ""
            }

            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                editTableText.value = ""
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }

            else -> {}
        }
    }
}