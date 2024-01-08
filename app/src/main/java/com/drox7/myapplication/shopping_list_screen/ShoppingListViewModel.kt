package com.drox7.myapplication.shopping_list_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository,
    private val categoryRepository: CategoryListRepository,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    val list = repository.getAllItem()

    val listCategoryFlow = categoryRepository.getAllItem()
    var originCategoryList = mutableListOf(CategoryItem(0,"Все"," ",""))
    var selectedTextCategory   = mutableStateOf(originCategoryList[0].name)
    var expandedCategory = mutableStateOf(false)
    var categoryId by mutableIntStateOf(0)


    var originShopList = listOf<ShoppingListItem>()
    var shopList by mutableStateOf(listOf<ShoppingListItem>())

    private val _uiEvent =
        Channel<UiEvent>() // transmitter for Broadcasting Events to View model and Compose
    val uiEvent = _uiEvent.receiveAsFlow() //receiver

    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf("")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(false)
        private set
    override var showEditSumText = mutableStateOf(false)
        private set

    override var titleColor = mutableStateOf("#FF3699E7")
        private set

    override val planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }
        }

        viewModelScope.launch {
            list.collect { list ->
                originShopList = list
                shopList = originShopList
                onEvent(ShoppingListEvent.OnGroupByCategory(categoryId))
                Log.d("MyLog","INIT")
            }
        }
        viewModelScope.launch {
            listCategoryFlow.collect { list ->
               originCategoryList= list.toMutableList()
                originCategoryList.add(originCategoryList.size,
                    CategoryItem(0,"Все"," ",""))
            }
        }
    }

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnItemSave -> {
                if (editTableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editTableText.value,
                            listItem?.time ?: getCurrentTime(),
                            listItem?.allItemCount ?: 0,
                            listItem?.allSelectedItemCount ?: 0
                        )
                    )
                }
            }

            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editTableText.value = listItem?.name ?: ""
                dialogTitle.value = "List name"
                showEditTableText.value = true

            }

            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Delete this item?"
                showEditTableText.value = false
            }

            is ShoppingListEvent.OnGroupByCategory -> {
                if(event.groupId != 0) {
                    shopList = originShopList. filter { shopListItem ->
                        shopListItem.categoryId == event.groupId
                    }
                } else shopList = originShopList
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                if (showEditTableText.value) {
                    onEvent(ShoppingListEvent.OnItemSave)
                } else {
                    viewModelScope.launch {
                        listItem?.let { repository.deleteItem(it) }
                    }
                }
                openDialog.value = false
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}