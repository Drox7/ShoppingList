package com.drox7.myapplication.add_item_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.AddItem
import com.drox7.myapplication.data.AddItemRepository
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
    categoryRepository: CategoryListRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemsList: Flow<List<AddItem>>? = null
    var addItem: AddItem? = null
    var shoppingListItem: ShoppingListItem? = null

    val listCategoryFlow = categoryRepository.getAllItem()
    var expandedCategory by mutableStateOf(false)
    var selectedTextCategory = ""
    var categoryId = 0
    var categoryList: List<CategoryItem> = emptyList()

    var listId: Int = -1
    var itemText = mutableStateOf("")
        private set
    var planSum = mutableFloatStateOf(0.00f)
        private set

    override var planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))

    var actualSum = mutableFloatStateOf(0.00f)
        private set
    override var dialogTitle = mutableStateOf("")
        private set
    override var editTableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(true)
        private set
    override var showEditSumText = mutableStateOf(true)
        private set

    override var titleColor = mutableStateOf("#FF3699E7")
        private set

    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemsList = repository.getAllItemById(listId)
        //Log.d("MyLog", "List id View model $listId")
        viewModelScope.launch {
            shoppingListItem = repository.getListItemById(listId)
            categoryId = shoppingListItem!!.categoryId
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }

        }

        viewModelScope.launch {
            listCategoryFlow.collect { list ->
                categoryList= list
            }
            selectedTextCategory = categoryList.find{
                it.id == categoryId
            }?.name ?: ""
        }

        updateShoppingListCount()

    }

    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch
                    if (addItem != null) {
                        if (addItem!!.name.isEmpty()) {
                            sendUiEvent(UiEvent.ShowSnackBar("Name must not be empty!"))
                            return@launch
                        }
                    } else {
                        if (itemText.value.isEmpty()) {
                            sendUiEvent(UiEvent.ShowSnackBar("Name must not be empty!"))
                            return@launch
                        }
                    }
                    repository.insertItem(
                        AddItem(
                            addItem?.id,
                            addItem?.name ?: itemText.value,
                            addItem?.isCheck ?: false,
                            listId,
                            addItem?.planSum ?:0.00f,
                            addItem?.actualSum ?:0.00f,
                        )
                    )
                    itemText.value = ""
                    addItem = null
                }
                updateShoppingListCount()
            }

            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editTableText.value = addItem?.name ?: ""
                planSumTextFieldValue.value = planSumTextFieldValue.value.copy(
                    text = addItem?.planSum.toString()
                )
                actualSumTextFieldValue.value = actualSumTextFieldValue.value.copy(
                    text = addItem?.actualSum.toString()
                )
                //editPlanSumText.value = addItem?.planSum.toString()
                //editActualSumText.value = addItem?.actualSum.toString()
            }

            is AddItemEvent.OnTextChange -> {
                itemText.value = event.text
            }

            is AddItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                updateShoppingListCount()
            }

            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
                updateShoppingListCount()
            }

        }

    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editTableText.value = ""
            }

            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                addItem = addItem?.copy(
                    name = editTableText.value,
                    planSum = planSumTextFieldValue.value.text.toFloat(),
                    actualSum = actualSumTextFieldValue.value.text.toFloat()
                )
                editTableText.value = ""
                onEvent(AddItemEvent.OnItemSave)
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }

            is DialogEvent.OnActualSumChange -> {
                actualSumTextFieldValue.value = event.textFieldValue
            }
            is DialogEvent.OnPlanSumChange -> {
                planSumTextFieldValue.value = event.textFieldValue
            }
        }
    }

     fun updateShoppingListCount() {
        viewModelScope.launch {
            itemsList?.collect { list ->
                var counter = 0
                var planSumTemp = 0.00f
                var actualSumTemp = 0.00f
                list.forEach { item ->
                    planSumTemp += item.planSum
                    actualSumTemp += item.actualSum
                    if (item.isCheck) counter++
                }
                planSum.floatValue = planSumTemp
                actualSum.floatValue = actualSumTemp
                shoppingListItem?.copy(
                    allItemCount = list.size,
                    allSelectedItemCount = counter,
                    planSum = planSum.floatValue,
                    actualSum = actualSum.floatValue,
                    categoryId = categoryId
                )?.let { shItem ->
                    repository.insertItem(
                        shItem
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}