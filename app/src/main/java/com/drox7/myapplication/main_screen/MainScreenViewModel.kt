package com.drox7.myapplication.main_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drox7.myapplication.data.ShoppingListItem
import com.drox7.myapplication.data.ShoppingListRepository
import com.drox7.myapplication.data.TransactionItem
import com.drox7.myapplication.data.TransactionItemRepository
import com.drox7.myapplication.datastore.DataStoreManager
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.dialog.DialogEvent
import com.drox7.myapplication.dialog.UiStateDialog
import com.drox7.myapplication.utils.Routes
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.getCurrentTime
import com.drox7.myapplication.utils.getCurrentTimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository,
    val repositoryTransaction: TransactionItemRepository,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    var categoryId = 0
    val uiEvent = _uiEvent.receiveAsFlow()

    //var titleColor = mutableStateOf("#FF3699E7")
    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editTableText = mutableStateOf("")
        private set
    override val planSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))

    override var actualSumTextFieldValue = mutableStateOf(TextFieldValue("0.00"))
    override val uiStateDialog: MutableState<UiStateDialog>
        get() = TODO("Not yet implemented")
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
                            categoryId = categoryId
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
                            getCurrentTimeStamp(),
                            editTableText.value,
                            true,
                            0,
                            sum = actualSumTextFieldValue.value.text.toFloat()
                        )
                    )
                }
            }


            is MainScreenEvent.OnNewItemClick -> {
                if (event.route == Routes.SHOPPING_LIST) {
                    openDialog.value = true
                }
                if (event.route == Routes.NOTE_LIST) {
                    sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
                }
                if (event.route == Routes.TRANSACTION_LIST) {
                    openDialog.value = true
                    showEditSumText.value = true
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
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}