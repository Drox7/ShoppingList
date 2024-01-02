package com.drox7.myapplication.new_note_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.NoteItem
import com.drox7.myapplication.data.NoteRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var noteId = -1
    private var noteItem: NoteItem? = null

    var titleColor = mutableStateOf("#FF3699E7")

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    init {
        noteId = savedStateHandle.get<String>("noteId")?.toInt() ?: -1
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }
        }
        if (noteId != -1) {
            viewModelScope.launch {
                repository.getNoteItemById(noteId).let { noteItem ->
                    title = noteItem.title
                    description = noteItem.description
                    this@NewNoteViewModel.noteItem = noteItem
                }
            }
        }
    }

    fun onEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.OnSave -> {
                viewModelScope.launch {
                    if (title.isEmpty()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                "Title can not be empty!"
                            )
                        )
                        return@launch
                    }
                    repository.insertItem(
                        NoteItem(
                            noteItem?.id,
                            title,
                            description,
                            getCurrentTime()
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }

            is NewNoteEvent.OnTitleChange -> {
                title = event.title
            }

            is NewNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}