package com.drox7.myapplication.note_list_screen

import com.drox7.myapplication.data.NoteItem

sealed class NoteListEvent {
    data class OnShowDeleteDialog(val item: NoteItem) : NoteListEvent()
    data class OnItemClick(val route: String) : NoteListEvent()
    data class OnTextSearchChange(val text: String) : NoteListEvent()
    object UnDoneDeleteItem: NoteListEvent()
}