package com.drox7.myapplication.dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drox7.myapplication.R
import com.drox7.myapplication.shopping_list_screen.ShoppingListViewModel
import com.drox7.myapplication.utils.sortList

@Composable
fun UiDropDownMenu(
    viewModel: ShoppingListViewModel
) {
    //val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 4.dp, end = 0.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            modifier = Modifier.background(colorScheme.onPrimary),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.orderA_zText)) },
                onClick = {
                    viewModel.shopList = sortList(viewModel.shopList, 1)
                    viewModel.sortId = 1
                    viewModel.setSortIdToDataManager()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.orderDateText)) },
                onClick = {
                    viewModel.shopList = sortList(viewModel.shopList, 0)
                    viewModel.sortId = 0
                    viewModel.setSortIdToDataManager()
                    expanded = false
                },
//                leadingIcon = {
//                    Icon(imageVector = Icons.Default.Check, contentDescription ="" )
//                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.orderfinishedText)) },
                onClick = {
//                    Toast.makeText(context,
//                        stringResource(R.string.orderfinishedText), Toast.LENGTH_SHORT).show()
                    viewModel.shopList = sortList(viewModel.shopList, 3)
                    viewModel.sortId = 3
                    viewModel.setSortIdToDataManager()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.orderEmptyText)) },
                onClick = {
                    //Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show()
                    viewModel.shopList = sortList(viewModel.shopList, 2)
                    viewModel.sortId = 2
                    viewModel.setSortIdToDataManager()
                    expanded = false
                }
            )
        }
    }
}