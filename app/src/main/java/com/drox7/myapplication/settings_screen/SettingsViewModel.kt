package com.drox7.myapplication.settings_screen

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.UnitItem
import com.drox7.myapplication.data.UnitItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.di.AppModule
import com.drox7.myapplication.di.AppModule.MainColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val categoryRepository: CategoryListRepository,
    private val unitRepository: UnitItemRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val colorItemListState = mutableStateOf<List<ColorItem>>(emptyList())
    val listCategoryFlow = categoryRepository.getAllItem()
    val listUnitFlow = unitRepository.getAllItem()
    var titleColor: String = "#FF3699E7"
    var scaleText = mutableFloatStateOf(0.5f)
    var itemCategoryText = mutableStateOf("")
        private set
    var itemUnitText = mutableStateOf("")
        private set
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
                titleColor = selectedColor
                MainColor = selectedColor
            }
        }
        viewModelScope.launch {
                dataStoreManager.getStringPreferences(
                    DataStoreManager.SCALE_TEXT_VALUE,
                    "0.5"
                ).collect { selectedScaleText ->
                    scaleText.floatValue = selectedScaleText.toFloat()
                    AppModule.scaleTextValue = selectedScaleText.toFloat()
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
            is SettingsEvent.OnCategoryItemSave ->{
                viewModelScope.launch {
                    categoryRepository.insertItem(
                        CategoryItem(
                            null,
                            itemCategoryText.value,
                            "",
                            ""
                        )
                    )
                    itemCategoryText.value = ""
                }
            }
            is SettingsEvent.OnDeleteCategory ->{
                viewModelScope.launch {
                    categoryRepository.deleteItem(event.item)
                }
            }
            is SettingsEvent.OnDeleteUnit ->{
                viewModelScope.launch {
                    unitRepository.deleteItem(event.item)
                }
            }
            is SettingsEvent.OnUnitItemSave ->{
                viewModelScope.launch {
                     unitRepository.insertItem(UnitItem(
                         null,
                         itemUnitText.value,
                         "",
                         isBase = true

                     ))
                    itemUnitText.value = ""

                }
            }
            is SettingsEvent.OnScaleTextChange -> {
                viewModelScope.launch {
                    dataStoreManager.saveStringPreferences(
                        event.scaleValue.toString(),
                        DataStoreManager.SCALE_TEXT_VALUE
                    )
                }
            }

            is SettingsEvent.OnTextCategoryChange -> {
                itemCategoryText.value = event.text
            }
            is SettingsEvent.OnTextUnitChange -> {
                itemUnitText.value = event.text
            }
        }
    }
}