package com.drox7.myapplication.settings_screen

import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.UnitItem

sealed class SettingsEvent {
    data class OnItemSelected(val color: String) : SettingsEvent()
    data class OnTextCategoryChange(val text: String): SettingsEvent()
    data class OnTextUnitChange(val text: String): SettingsEvent()
    data class OnScaleTextChange(val scaleValue: Float): SettingsEvent()
    object OnCategoryItemSave: SettingsEvent()
    data class OnDeleteCategory(val item: CategoryItem): SettingsEvent()
    data class OnDeleteUnit(val item: UnitItem): SettingsEvent()
    object OnUnitItemSave: SettingsEvent()
}