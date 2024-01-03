package com.drox7.myapplication.shopping_list_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.main_screen.UiTopBar
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val itemsList = viewModel.list.collectAsState(initial = emptyList())
    val titleColor = Color(android.graphics.Color.parseColor(viewModel.titleColor.value))
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEven ->
            when (uiEven) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEven.route)
                }

                else -> {}
            }
        }
    }
    Scaffold(
        backgroundColor = colorScheme.background,
        topBar = {
            UiTopBar(
                titleColor = titleColor,
                onClick = { /*TODO*/ },
                titleText = "Список покупок",
                iconVector = Icons.Filled.Menu
            )
        },
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)

        ) {
            items(itemsList.value) { item ->
                UiShoppingListItem(titleColor, item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }
        MainDialog(viewModel)
        if (itemsList.value.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = "EMPTY",
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}