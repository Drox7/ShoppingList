package com.drox7.myapplication.transaction_list_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.TransactionItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.expandableElements.ExpandableCardController
import com.drox7.myapplication.expandableElements.ExpandableCardEvent
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuState
import com.drox7.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class TransactionItemViewModel @Inject constructor(
    private val repository: TransactionItemRepository,
    categoryRepository: CategoryListRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController, ExpandableCardController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val listCategoryFlow = categoryRepository.getAllItem()
    var itemsList: Flow<List<TransactionItem>>? = null
    var transactionItem: TransactionItem? = null
    //var categoryId = 0
    private var categoryList: List<CategoryItem> = emptyList()
    override var dateTimeItemMillis = mutableLongStateOf (System.currentTimeMillis())
    private set

    override var dropDownMenuState = mutableStateOf(DropDownMenuState(
        mutableStateOf(false), categoryList, CategoryItem(0,"","","")))
        //private set

    var listId: Int = -1
    var itemText = mutableStateOf("")
        private set
    override var description = mutableStateOf("")
        private set
    var sum = mutableFloatStateOf(0.00f)

    var summarySum = mutableFloatStateOf(0.00f)
    var summarySumToday = mutableFloatStateOf(0.00f)
    var summarySumMonth = mutableFloatStateOf(0.00f)


    override var planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))

    override var quantity = mutableStateOf(TextFieldValue("0.00"))
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

    private val formatterDay = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val formatterMonth = SimpleDateFormat("MM.yyyy", Locale.getDefault())

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
        viewModelScope.launch {
            listCategoryFlow.collect { list ->
                //categoryList = list
                dropDownMenuState.value.listItemsMenu=list
            }
        }
        updateTransactionList()
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
                            transactionItem?.dateTime ?: Timestamp(dateTimeItemMillis.longValue),
                            transactionItem?.name ?: itemText.value,
                            true,
                            categoryId = transactionItem?.categoryId ?: 0,
                            sum = transactionItem?.sum
                                ?: actualSumTextFieldValue.value.text.toFloat(),
                            quantity = transactionItem?.quantity ?: quantity.value.text.toFloat()
                        )
                    )
                    itemText.value = ""
                    transactionItem = null
                }
                updateTransactionList()
            }

            is TransactionItemEvent.OnShowEditDialog -> {
                transactionItem = event.item
                openDialog.value = true
                dialogTitle.value = ""
                showEditTableText.value = true
                showEditSumText.value = true

                editTableText.value = transactionItem?.name ?: ""
                actualSumTextFieldValue.value = actualSumTextFieldValue.value.copy(
                    text = transactionItem?.sum.toString()
                )
                quantity.value = quantity.value.copy(
                    text = transactionItem?.quantity.toString()
                )
                dropDownMenuState.value.selectedItem = dropDownMenuState.value.listItemsMenu.find { it.id == transactionItem?.categoryId} ?: CategoryItem(0,"","","")
                dateTimeItemMillis.longValue= transactionItem?.dateTime?.time ?: System.currentTimeMillis()
            }

            is TransactionItemEvent.OnTextChange -> {
                itemText.value = event.text
            }

            is TransactionItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                updateTransactionList()
            }

            is TransactionItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
                // updateShoppingList()
            }

            is TransactionItemEvent.OnShowDeleteDialog -> {
                transactionItem = event.item
                openDialog.value = true
                dialogTitle.value = "Удалить ${event.item.name}?"
                showEditTableText.value = false
                showEditSumText.value = false
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
                if (showEditTableText.value) {
                    transactionItem = transactionItem?.copy(
                        name = editTableText.value,
                        sum = actualSumTextFieldValue.value.text.toFloat(),
                        quantity = quantity.value.text.toFloat(),
                        dateTime = Timestamp(dateTimeItemMillis.longValue),
                        categoryId = dropDownMenuState.value.selectedItem.id ?:0
                    )
                    editTableText.value = ""
                    onEvent(TransactionItemEvent.OnItemSave)
                } else {
                    viewModelScope.launch {
                        transactionItem?.let { repository.deleteItem(it) }
                    }
                }
                openDialog.value = false
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

            is DialogEvent.OnQuantityChange -> {
                quantity.value = event.textFieldValue
            }


        }
    }

    private fun updateTransactionList() {
        viewModelScope.launch {
            itemsList?.collect { list ->
                var sumTemp = 0f
                var sumTempToday = 0f
                var sumTempMonth = 0f
                val listToday = list.filter { formatterDay.format(it.dateTime?.time) == formatterDay.format(Calendar.getInstance().time)}
                val listMonth = list.filter { formatterMonth.format(it.dateTime?.time) == formatterMonth.format(Calendar.getInstance().time)}
                //val listPreviousMonth = list.filter { it.dateTime?.}
                list.forEach { item ->
                    sumTemp += item.sum
                }

                listToday.forEach{item ->
                    sumTempToday += item.sum
                }
                listMonth.forEach{item ->
                    sumTempMonth += item.sum
                }
                summarySum.floatValue = sumTemp
                summarySumToday.floatValue = sumTempToday
                summarySumMonth.floatValue = sumTempMonth
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}