package com.drox7.myapplication.expandableElements

sealed class ExpandableCardEvent {
    data class OnTextChange(val text: String) : ExpandableCardEvent()
}
