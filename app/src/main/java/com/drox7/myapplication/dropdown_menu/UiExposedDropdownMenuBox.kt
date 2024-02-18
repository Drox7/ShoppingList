package com.drox7.myapplication.dropdown_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.di.AppModule.MainColor
import com.drox7.myapplication.shopping_list_screen.ShoppingListEvent
import com.drox7.myapplication.shopping_list_screen.ShoppingListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiExposedDropdownMenuBox(
    //listMenuItems: MutableList<CategoryItem>,
    viewModel: ShoppingListViewModel
    //dropDownMenuController: DropDownMenuController,
    //onClick: (ShoppingListEvent) -> Unit,
) {
    //val context = LocalContext.current

   // var expandedCategory by remember { mutableStateOf(false) }
//    var selectedTextCategory by remember { mutableStateOf("All") }
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
            TextField(
                value = viewModel.selectedTextCategory,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                ),
//                label = {
//                    Text(
//                        text = "Group",
//                        color = md_theme_light_tertiary,
//                        fontSize = 8.sp
//                    )
//                },
                maxLines =1,
               // overflow = TextOverflow.Ellipsis,
                leadingIcon = { TrailingIcon(expanded = viewModel.expandedCategory) },
                //trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier

                    .menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color(android.graphics.Color.parseColor(MainColor))
                )
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary),
                expanded = viewModel.expandedCategory,
                onDismissRequest = { viewModel.expandedCategory = false }
            ) {
                viewModel.originCategoryList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(
                            text = item.name,
                            style = TextStyle(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        ) },
                        onClick = {
                            viewModel.expandedCategory = false
                            viewModel.onEvent(ShoppingListEvent.OnGroupByCategory(item.id?:0, item.name ))
                            viewModel.selectedTextCategory = item.name
                            viewModel.categoryId= item.id?:0
                           // Toast.makeText(context, item.id.toString(), Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}