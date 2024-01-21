package com.drox7.myapplication.transaction_list_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.TransactionItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.dialog.UiStateDialog
import com.drox7.myapplication.expandableElements.ExpandableCardController
import com.drox7.myapplication.expandableElements.ExpandableCardEvent
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.getCurrentTimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class TransactionItemViewModel @Inject constructor(
    private val repository: TransactionItemRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController, ExpandableCardController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemsList: Flow<List<TransactionItem>>? = null
    var transactionItem: TransactionItem? = null
    var expandedCategory by mutableStateOf(false)
    var selectedTextCategory = ""
    var categoryId = 0
    var categoryList: List<CategoryItem> = emptyList()

    var listId: Int = -1
    var itemText = mutableStateOf("")
        private set
    override var description = mutableStateOf("")
        private set
    var sum = mutableFloatStateOf(0.00f)
    var summarySum = mutableFloatStateOf(0.00f)
        private set

    override var planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override val uiStateDialog: MutableState<UiStateDialog>
        get() = TODO("Not yet implemented")

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

        viewModelScope.launch {
            itemsList = repository.getAllItem()
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }

        }
      //  updateShoppingList()
    }

    fun onEvent(event: TransactionItemEvent) {
        when (event) {
            is TransactionItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (transactionItem != null) {
                        if (transactionItem!!.name.isEmpty()) {
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
                        TransactionItem(
                            transactionItem?.id,
                            getCurrentTimeStamp(),
                            transactionItem?.name ?: itemText.value,
                            true,
                            sum = transactionItem?.sum ?: actualSumTextFieldValue.value.text.toFloat()
                        )
                    )
                    itemText.value = ""
                    transactionItem = null
                }
              //  updateShoppingList()
            }

            is TransactionItemEvent.OnShowEditDialog -> {
                transactionItem = event.item
                openDialog.value = true
                editTableText.value = transactionItem?.name ?: ""
                actualSumTextFieldValue.value = actualSumTextFieldValue.value.copy(
                    text = transactionItem?.sum.toString()
                )
                UiStateDialog(null, null,transactionItem = transactionItem)
            }

            is TransactionItemEvent.OnTextChange -> {
                itemText.value = event.text
            }

            is TransactionItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                //updateShoppingList()
            }

            is TransactionItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
               // updateShoppingList()
            }
        }

    }

    override fun onCardEvent(event: ExpandableCardEvent) {
        when (event) {
            is ExpandableCardEvent.OnTextChange -> {
                description.value = event.text
               // updateShoppingList()
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
                transactionItem = transactionItem?.copy(
                    name = editTableText.value,
                    sum = actualSumTextFieldValue.value.text.toFloat()
                )
                editTableText.value = ""
                onEvent(TransactionItemEvent.OnItemSave)
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

//    fun updateTransactionList() {
//        viewModelScope.launch {
//            itemsList?.collect { list ->
//                var counter = 0
//                var planSumTemp = 0.00f
//                var actualSumTemp = 0.00f
//                list.forEach { item ->
//                    planSumTemp += item.planSum
//                    actualSumTemp += item.actualSum
//                    if (item.isCheck) counter++
//                }
//                planSum.floatValue = planSumTemp
//                actualSum.floatValue = actualSumTemp
//
//                shoppingListItem?.copy(
//                    allItemCount = list.size,
//                    allSelectedItemCount = counter,
//                    planSum = planSum.floatValue,
//                    actualSum = actualSum.floatValue,
//                    categoryId = categoryId,
//                    description = description.value
//                )?.let { shItem ->
//                    repository.insertItem(
//                        shItem
//                    )
//                }
//            }
//        }
//    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}