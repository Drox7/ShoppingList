package com.drox7.myapplication.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.ui.theme.GrayLight


@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
   val itemsList = viewModel.list.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayLight),
        contentPadding = PaddingValues(bottom = 100.dp)

    ) {
        items(itemsList.value){item ->
            UiShoppingListItem(item){event ->
                viewModel.onEvent(event)
            }
        }
    }
    MainDialog(viewModel)
}