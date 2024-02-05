package com.drox7.myapplication.ui_drop_down_menu_box

import androidx.compose.runtime.MutableState
import com.drox7.myapplication.data.CategoryItem

data class DropDownMenuStateCategory(
    var expandedState: MutableState<Boolean>,
    var listItemsMenu: List<CategoryItem>,
    //var listCategoryFlow: Flow<List<CategoryItem>>,
    var selectedItem : CategoryItem,
   // var selectedText: String
    //val titleColor: Color
)

