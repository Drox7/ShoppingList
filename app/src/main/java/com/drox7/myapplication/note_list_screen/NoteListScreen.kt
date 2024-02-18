package com.drox7.myapplication.note_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
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
import com.drox7.myapplication.di.AppModule.MainColor
import com.drox7.myapplication.dialog.MainDialog
import com.drox7.myapplication.ui.theme.md_theme_light_tertiary
import com.drox7.myapplication.utils.SwipeToDeleteContainer
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    //val itemsList = viewModel.noteList.collectAsState(initial = emptyList())
    val titleColor = Color(android.graphics.Color.parseColor(MainColor))
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEven ->
            when (uiEven) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEven.route)
                }

                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEven.message,
                        actionLabel = "Undone"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.UnDoneDeleteItem)
                    }
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
                    modifier = Modifier.padding(bottom = 100.dp),
                    actionColor = Color.White
                )

            }
        },
        topBar = {
//            UiTopBar(
//                titleColor = titleColor,
//                onClick = { /*TODO*/ },
//                titleText = "Заметки",
//                iconVector = Icons.Filled.Menu
//            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Card(
                elevation = 0.dp,
                modifier = Modifier
                    .background(colorScheme.background)
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 5.dp),
                //shape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp),
            ) {
                TextField(
                    shape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    singleLine = true,
                    value = viewModel.searchText,
                    onValueChange = { text ->
                        viewModel.onEvent(NoteListEvent.OnTextSearchChange(text))
                    },
                    placeholder = {
                        Text(
                            text = "Search...",
                            color = md_theme_light_tertiary,
                            fontSize = 12.sp
                        )
                    },

                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = md_theme_light_tertiary,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorScheme.onPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = titleColor,
                        textColor = titleColor,
                    ),
                )
            }

            LazyColumn(
                modifier = Modifier
                    .background(colorScheme.background)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)

            ) {
                items(
                    viewModel.noteList,
                    key = {it.id ?: 0}
                ) { item ->
                    SwipeToDeleteContainer(
                        item = item,
                        onDelete = {
                            viewModel.onEvent(NoteListEvent.OnShowDeleteDialog(item))
                        })
                    {
                        UiNoteItem(titleColor, item) { event ->
                            viewModel.onEvent(event)
                        }
                    }
                }
            }
            MainDialog(viewModel)
            if (viewModel.noteList.isEmpty()) {
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
}