package com.drox7.myapplication.settings_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val colorItemListState = mutableStateOf<List<ColorItem>>(emptyList())

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { selectedColor ->
                val tempColorItemList = ArrayList<ColorItem>()
                ColorUtils.colorList.forEach { color ->
                    tempColorItemList.add(
                        ColorItem(
                            color,
                            selectedColor == color
                        )
                    )
                }
                colorItemListState.value = tempColorItemList
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnItemSelected -> {
                viewModelScope.launch {
                    dataStoreManager.saveStringPreferences(
                        event.color,
                        DataStoreManager.TITLE_COLOR
                    )
                }
            }
        }
    }
}