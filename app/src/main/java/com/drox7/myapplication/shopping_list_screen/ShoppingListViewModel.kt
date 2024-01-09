package com.drox7.myapplication.shopping_list_screen

import android.annotation.SuppressLint
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
import com.drox7.myapplication.utils.groupByCategory
import com.drox7.myapplication.utils.sortList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository,
    categoryRepository: CategoryListRepository,
    private var dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    val list = repository.getAllItem()

    private val listCategoryFlow = categoryRepository.getAllItem()
    var originCategoryList = mutableListOf(CategoryItem(0, "Все", " ", ""))
    var selectedTextCategory : String = "Все"
    var expandedCategory by mutableStateOf(false)
    var categoryId =0
    var sortId by mutableIntStateOf(0)

    private var originShopList = listOf<ShoppingListItem>()
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
       getCategoryFromDataManager()
        getOrderId()

        viewModelScope.launch {
            list.collect { list ->
                originShopList = list
                shopList = groupByCategory(originShopList, categoryId)
                shopList = sortList(shopList, sortId)
                Log.d("MyLog", "catId ${categoryId} sortId ${sortId} INIT")
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

    fun setSortIdToDataManager() {
        viewModelScope.launch {
            dataStoreManager.saveStringPreferences(
                sortId.toString(),
                DataStoreManager.SORT_ID
            )
            Log.d("Mylog", " setSortIdDM $sortId.toString()")
        }
    }
    private fun getCategoryFromDataManager() {
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.CATEGORY_ID,
                "0"
            ).collect { id ->
                categoryId = id.toInt()
                Log.d("Mylog", "get DM $id")
            }
        }

        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.CATEGORY_NAME,
                "Все"
            ).collect { catName ->
                selectedTextCategory = catName
                Log.d("Mylog", "get DM $catName")
            }
        }
    }

    private fun getOrderId() {
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.SORT_ID,
                "0"
            ).collect { id ->
                sortId = id.toInt()
               // Log.d("Mylog", "id ${id}")
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

                viewModelScope.launch {
                    dataStoreManager.saveStringPreferences(
                        event.groupId.toString(),
                        DataStoreManager.CATEGORY_ID
                    )
                    Log.d("MyLog"," Save ${event.groupId}")
                }
                viewModelScope.launch {
                    dataStoreManager.saveStringPreferences(
                        event.categoryName,
                        DataStoreManager.CATEGORY_NAME
                    )
                    Log.d("MyLog"," Save ${event.categoryName}")
                }
                shopList = groupByCategory(originShopList, event.groupId)
                shopList = sortList(shopList, sortId)
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