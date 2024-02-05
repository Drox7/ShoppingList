package com.drox7.myapplication.note_list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.NoteItem
import com.drox7.myapplication.data.NoteRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuState
import com.drox7.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    dataStoreManager: DataStoreManager

) : ViewModel(), DialogController {
    val noteListFlow = repository.getAllItems()
    private var noteItem: NoteItem? = null

    var noteList by mutableStateOf(listOf<NoteItem>())
    var originNoteList = listOf<NoteItem>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var searchText by mutableStateOf("")
        private set
    override var dialogTitle = mutableStateOf("Delete this note?")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override val planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override val quantity: MutableState<TextFieldValue>
        get() = TODO("Not yet implemented")
    override var dateTimeItemMillis = mutableLongStateOf(1)
    override val dropDownMenuState: MutableState<DropDownMenuState>
        get() = TODO("Not yet implemented")
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(false)
        private set
    override var showEditSumText = mutableStateOf(false)
        private set
    override var titleColor = mutableStateOf("#FF3699E7")
        private set

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }
        }

        viewModelScope.launch {
            noteListFlow.collect{list ->
                noteList = list
                originNoteList = list
            }
        }
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.OnTextSearchChange -> {
                searchText = event.text
                noteList = originNoteList.filter {note ->
                    note.title.lowercase().startsWith(searchText.lowercase())
                }
            }
            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }

            is NoteListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is NoteListEvent.UnDoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }

            else -> {}
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    repository.deleteItem(noteItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar("Undone delete item?"))
                }
                openDialog.value = false
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}