package com.drox7.myapplication.expandableElements

import androidx.compose.runtime.MutableState

interface ExpandableCardController {
    val description: MutableState<String>
    val titleColor: MutableState<String>
    fun onCardEvent(event: ExpandableCardEvent)
}