package com.drox7.myapplication.ui_drop_down_menu_box

import androidx.compose.runtime.MutableState
import com.drox7.myapplication.data.UnitItem

data class DropDownMenuStateUnit(
    var expandedState: MutableState<Boolean>,
    var listItemsMenu: List<UnitItem>,
    var selectedItem : UnitItem,
    //val titleColor: Color
)

