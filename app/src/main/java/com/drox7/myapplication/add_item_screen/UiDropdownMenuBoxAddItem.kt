package com.drox7.myapplication.add_item_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiDropdownMenuBoxAddItem(
    viewModel: AddItemViewModel
) {
    val categoryList = viewModel.listCategoryFlow.collectAsState(initial = emptyList())
    viewModel.selectedTextCategory = viewModel.categoryList.find {
        it.id == viewModel.categoryId
    }?.name ?: ""
    Box(
        modifier = Modifier
            //.width(200.dp)
            // .fillMaxWidth()
            .padding(0.dp),
        contentAlignment = Alignment.Center

    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier,
            expanded = viewModel.expandedCategory,
            onExpandedChange = {
                viewModel.expandedCategory = !viewModel.expandedCategory
            }
        ) {
            OutlinedTextField(
                value = viewModel.selectedTextCategory,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.End
                ),
//                label = {
//                    Text(
//                        text = "Category",
//                        color = md_theme_light_tertiary,
//                        fontSize = 8.sp
//                    )
//                },
                maxLines = 1,
                // overflow = TextOverflow.Ellipsis,
                trailingIcon = { TrailingIcon(expanded = viewModel.expandedCategory) },
                //trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier

                    .menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(android.graphics.Color.parseColor(viewModel.titleColor.value))
                )
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary),
                expanded = viewModel.expandedCategory,
                onDismissRequest = { viewModel.expandedCategory = false }
            ) {
                categoryList.value.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item.name,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    //textAlign = TextAlign.start
                                )
                            )

                        },
                        onClick = {
                            viewModel.expandedCategory = false
//                            viewModel.onEvent(ShoppingListEvent.OnGroupByCategory(item.id?:0, item.name ))
                            viewModel.selectedTextCategory = item.name
                            viewModel.categoryId = item.id ?: 0
                            viewModel.updateShoppingListCount()
                            // Toast.makeText(context, item.id.toString(), Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}