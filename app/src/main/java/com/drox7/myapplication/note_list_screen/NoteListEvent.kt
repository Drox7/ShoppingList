package com.drox7.myapplication.note_list_screen

import com.drox7.myapplication.data.NoteItem

sealed class NoteListEvent {
    data class OnShowDeleteDiaolog(val item: NoteItem) : NoteListEvent()
    data class OnItemClick(val route: String) : NoteListEvent()
}