package com.drox7.myapplication.main_screen

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.CategoryItem
import com.drox7.myapplication.data.CategoryListRepository
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.TransactionItemRepository
import com.drox7.myapplication.data.UnitItem
import com.drox7.myapplication.data.UnitItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuStateCategory
import com.drox7.myapplication.ui_drop_down_menu_box.DropDownMenuStateUnit
import com.drox7.myapplication.utils.Routes
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository,
    categoryRepository: CategoryListRepository,
    unitRepository: UnitItemRepository,
    private val repositoryTransaction: TransactionItemRepository,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    var categoryId = 0
    val uiEvent = _uiEvent.receiveAsFlow()

    //var titleColor = mutableStateOf("#FF3699E7")
    override var dialogTitle = mutableStateOf("")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override val planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))

    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override val quantity = mutableStateOf(TextFieldValue("0.00"))
    override val dateTimeItemMillis = mutableLongStateOf(System.currentTimeMillis())
    val listCategoryFlow = categoryRepository.getAllItem()
    private var categoryList: List<CategoryItem> = emptyList()
    private var unitList: List<UnitItem> = emptyList()
    override var dropDownMenuStateCategory = mutableStateOf(DropDownMenuStateCategory(
        mutableStateOf(false), categoryList, CategoryItem(0,"","","")
    ))
    override var dropDownMenuStateUnit = mutableStateOf(DropDownMenuStateUnit(
        mutableStateOf(false), unitList, UnitItem(1,"шт.","pc.",1.00f,"pice",true)
    ))
    private val listUnitFlow = unitRepository.getAllItem()
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditTableText = mutableStateOf(true)
        private set
    override var showEditSumText = mutableStateOf(false)
        private set
    var showFloatingButton = mutableStateOf(true)
        private set

    override var titleColor = mutableStateOf("#FF3699E7")
        private set

    init {
        viewModelScope.launch {
            listCategoryFlow.collect { list ->
                //categoryList = list
                dropDownMenuStateCategory.value.listItemsMenu=list
            }
        }
        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.TITLE_COLOR,
                "#FF3699E7"
            ).collect { color ->
                titleColor.value = color
            }
        }

        viewModelScope.launch {
            dataStoreManager.getStringPreferences(
                DataStoreManager.CATEGORY_ID,
                "0"
            ).collect { id ->
                categoryId = id.toInt()
                // Log.d("Mylog", "get DM $id")
            }
        }
        viewModelScope.launch {
            listUnitFlow.collect { list ->
                dropDownMenuStateUnit.value.listItemsMenu=list
            }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnItemSave -> {
                if (editTableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editTableText.value,
                            getCurrentTime(),
                            0,
                            0,
                            planSumTextFieldValue.value.text.toFloat(),
                            actualSumTextFieldValue.value.text.toFloat(),
                            categoryId = categoryId,


                        )
                    )
                }
            }

            is MainScreenEvent.OnItemSaveTransaction -> {
                if (editTableText.value.isEmpty()) return
                viewModelScope.launch {
                    repositoryTransaction.insertItem(
                        TransactionItem(
                            null,
                            Timestamp(dateTimeItemMillis.longValue),
                            editTableText.value,
                            true,
                            unitId = dropDownMenuStateUnit.value.selectedItem.id ?:0,
                            categoryId =  dropDownMenuStateCategory.value.selectedItem.id ?:0,
                            sum = actualSumTextFieldValue.value.text.toFloat(),
                            quantity = quantity.value.text.toFloat()
                        )
                    )
                }
            }


            is MainScreenEvent.OnNewItemClick -> {
                if (event.route == Routes.SHOPPING_LIST) {
                    dialogTitle.value ="Новый список"
                    openDialog.value = true
                    showEditSumText.value = false

                }
                if (event.route == Routes.NOTE_LIST) {
                    sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
                }
                if (event.route == Routes.TRANSACTION_LIST) {
                    dialogTitle.value ="Новый расход"
                    openDialog.value = true
                    dateTimeItemMillis.longValue =System.currentTimeMillis()
                    showEditSumText.value = true
                    dropDownMenuStateCategory.value.selectedItem = CategoryItem(0,"","","")
                }


            }

            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                showFloatingButton.value = !(event.route == Routes.ABOUT
                        || event.route == Routes.SETTINGS)
            }

            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))

            }

        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                if (showEditTableText.value&&(!showEditSumText.value)) {
                    onEvent(MainScreenEvent.OnItemSave)
                    editTableText.value = ""
                }

                if (showEditTableText.value&&(showEditSumText.value)) {
                    onEvent(MainScreenEvent.OnItemSaveTransaction)
                    editTableText.value = ""
                    actualSumTextFieldValue.value = actualSumTextFieldValue.value.copy(
                        text = "0.00"
                    )
                }

                openDialog.value = false
            }

            is DialogEvent.OnTextChange -> {
                editTableText.value = event.text
            }
            is DialogEvent.OnActualSumChange -> {
                actualSumTextFieldValue.value =event.textFieldValue
            }

            is DialogEvent.OnQuantityChange -> {
                quantity.value =event.textFieldValue
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