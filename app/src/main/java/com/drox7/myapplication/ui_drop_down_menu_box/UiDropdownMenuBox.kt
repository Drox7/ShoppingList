package com.drox7.myapplication.ui_drop_down_menu_box

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiDropdownMenuBox(
    dropDownMenuState: DropDownMenuState
) {
    Box(
        modifier = Modifier
            //.width(200.dp)
            // .fillMaxWidth()
            .padding(0.dp),
        contentAlignment = Alignment.Center

    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier,
            expanded = dropDownMenuState.expandedState.value,
            onExpandedChange = {
                dropDownMenuState.expandedState.value = !dropDownMenuState.expandedState.value
            }
        ) {
            OutlinedTextField(
                value = dropDownMenuState.selectedItem.name,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                label = {
                    if(dropDownMenuState.selectedItem.id ==0)
                    Text(
                        text = stringResource(R.string.categoryTitle),
                        color = md_theme_light_tertiary,
                        fontSize = 14.sp
                    )
                },
                maxLines = 1,
                // overflow = TextOverflow.Ellipsis,
                leadingIcon = { TrailingIcon(expanded = dropDownMenuState.expandedState.value) },
                //trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier

                    .menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                   // cursorColor = dropDownMenuState.titleColor
                )
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary),
                expanded = dropDownMenuState.expandedState.value,
                onDismissRequest = { dropDownMenuState.expandedState.value = false }
            ) {
                dropDownMenuState.listItemsMenu.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item.name,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    //textAlign = TextAlign.start
                                )
                            )

                        },
                        onClick = {
                            dropDownMenuState.expandedState.value = false
//                            viewModel.onEvent(ShoppingListEvent.OnGroupByCategory(item.id?:0, item.name ))
                            dropDownMenuState.selectedItem = item
                            //viewModel.categoryId = item.id ?: 0
                            // Toast.makeText(context, item.id.toString(), Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}