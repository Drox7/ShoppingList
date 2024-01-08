package com.drox7.myapplication.shopping_list_screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.dropdown_menu.UiDropDownMenu
import com.drox7.myapplication.dropdown_menu.UiExposedDropdownMenuBox
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    //val itemsList = viewModel.list.collectAsState(initial = emptyList())
   // val itemsListCategory = viewModel.listCategory.collectAsState(initial = emptyList())
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(modifier = Modifier
                    .background(colorScheme.onPrimary)
                    .fillMaxWidth()
                ) {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = titleColor
                        )
                    }
                    UiExposedDropdownMenuBox(viewModel
                    )
                    UiDropDownMenu()

                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = titleColor
                        )
                    }

                }
            }


//            UiTopBar(
//                titleColor = titleColor,
//                onClick = { /*TODO*/ },
//                titleText = "Список покупок",
//                iconVector = Icons.Filled.Menu
//            )
        },
    ) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)

        ) {
            items(viewModel.shopList) { item ->
                UiShoppingListItem(titleColor, item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }
        MainDialog(viewModel)
        if (viewModel.shopList.isEmpty()) {
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