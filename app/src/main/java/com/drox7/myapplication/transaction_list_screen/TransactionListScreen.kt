package com.drox7.myapplication.transaction_list_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.expandableElements.ExpandableCard
import com.drox7.myapplication.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionListScreen(
    viewModel: TransactionItemViewModel = hiltViewModel(),
   // onPopBackStack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val titleColor = Color(android.graphics.Color.parseColor(viewModel.titleColor.value))
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEven ->
            when (uiEven) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEven.message
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(uiEven.route)
                }

                else -> {}
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = titleColor,
                    actionColor = androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(10.dp),
                    // shape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colorScheme.onPrimary,
                elevation = 10.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier,
                    ) {
                        Text(
                            text = "Summary: ${viewModel.summarySum.floatValue} р.",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }

        },
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                           // onPopBackStack()
                            //viewModel.updateShoppingList()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.padding(top = 14.dp),
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = titleColor
                        )
                    }

                    Text(
                        modifier = Modifier
                            //.weight(2f)
                            .padding(top = 16.dp, start = 10.dp, end = 20.dp),
                        text = "Расходы",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            }

        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.6f)
                    // .fillMaxSize()
                    .padding(
                        // top = 3.dp,
                        start = 2.dp,
                        end = 2.dp
                    )
            ) {
                if (itemsList != null) {
                    items(itemsList.value) { item ->
                        UiTransactionItem(titleColor, item = item, onEvent = { event ->
                            viewModel.onEvent(event)
                        })
                    }
                }
            }
            ExpandableCard(viewModel ,title = "Описание...", )
        }
        MainDialog(dialogController = viewModel)
    }
}