package com.drox7.myapplication.settings_screen

sealed class SettingsEvent {
    data class OnItemSelected(val color: String) : SettingsEvent()
}