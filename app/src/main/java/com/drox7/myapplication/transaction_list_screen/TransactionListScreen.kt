package com.drox7.myapplication.transaction_list_screen


import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.di.AppModule.MainColor
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.expandableElements.ExpandableCard
import com.drox7.myapplication.utils.SwipeToDeleteContainer
import com.drox7.myapplication.utils.UiEvent
import com.drox7.myapplication.utils.formatDate
import com.drox7.myapplication.utils.getScaleText

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionListScreen(
    viewModel: TransactionItemViewModel = hiltViewModel(),
    // onPopBackStack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val titleColor = Color(android.graphics.Color.parseColor(MainColor))
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())

    val dateList = itemsList?.value?.groupBy(
        keySelector = { formatDate(it.dateTime) },
        valueTransform = { it }
    )

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
//                    IconButton(
//                        onClick = {
//                            // onPopBackStack()
//                            //viewModel.updateShoppingList()
//                        }
//                    ) {
//                        Icon(
//                            modifier = Modifier.padding(top = 14.dp),
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = titleColor
//                        )
//                    }

                    Text(
                        modifier = Modifier
                            //.weight(2f)
                            .padding(top = 16.dp, start = 10.dp, end = 20.dp, bottom = 10.dp),
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
                .animateContentSize(


                )
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
                    .animateContentSize(
                       // animationSpec = spring(1f,200f,)
//                animationSpec = tween(
//                    durationMillis = 200,
//                    easing = LinearEasing
//                )
                    )
            ) {
                if (itemsList != null) {
                    dateList?.forEach { category ->
                        stickyHeader {
                            Card(
                                shape = RoundedCornerShape(0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp)
                                    .background(MaterialTheme.colorScheme.background)

                            ) {
                                Text(
                                    text = category.key,
                                    modifier = Modifier
                                        //.fillMaxWidth()
                                        .padding(start = 5.dp, top = 8.dp, bottom = 8.dp)
                                    //.background(MaterialTheme.colorScheme.background)
                                    ,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontSize = 14.sp*(getScaleText(viewModel.scaleText))
                                )
                            }
                        }

                        items(
                            category.value,
                            key = {it.id ?:0}
                        ) { item ->
                            SwipeToDeleteContainer(
                                item = item,
                                onDelete = {
                                    viewModel.onEvent(TransactionItemEvent.OnDelete(item))
                                })
                            {
                                UiTransactionItem(
                                    viewModel.scaleText,
                                    viewModel.categoryList.find { it.id == item.categoryId }?.name
                                        ?: "",
                                    titleColor,
                                    item = item,
                                    onEvent = { event ->
                                        viewModel.onEvent(event)
                                    })
                            }
                        }
                    }
                }
            }
            ExpandableCard(
                viewModel,
                title = "Всего расходов:",
                bottomPadding = 55.dp,
                showSummary = true,
                summarySum = viewModel.summarySum,
                summarySumToday = viewModel.summarySumToday.value,
                //summarySumMonth = viewModel.summarySumMonth,
                summaryItemList = viewModel.summaryList,
                summaryMonthMap = viewModel.mapSummaryMonth.value
            )
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .padding(bottom = 55.dp)
//                    .fillMaxWidth()
//                ,
//            ) {
//                Row(
//                    modifier = Modifier,
//                ) {
//                    Text(
//                        text = "Итого: ${viewModel.summarySum.floatValue}₽",
//                        modifier = Modifier.padding(10.dp),
//                        fontSize = 14.sp
//                    )
//                }
//            }
        }
        MainDialog(dialogController = viewModel, showDropDownMenu = true)
    }
}