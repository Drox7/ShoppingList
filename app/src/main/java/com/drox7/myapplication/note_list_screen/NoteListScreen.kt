package com.drox7.myapplication.note_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
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
import com.drox7.myapplication.ui.theme.BlueLight
import com.drox7.myapplication.utils.UiEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.noteList.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEven ->
            when (uiEven) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEven.route)
                }
                is UiEvent.ShowSnackBar -> {
                   val result =  scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEven.message,
                        actionLabel = "Undone"
                    )
                    if (result==SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.UnDoneDeleteItem)
                    }
                }
                else -> {}
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState){data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight,
                modifier = Modifier.padding(bottom = 100.dp),
                actionColor = Color.White
            )

        }
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentPadding = PaddingValues(bottom = 100.dp)

        ) {
            items(itemsList.value) { item ->
                UiNoteItem(viewModel.titleColor.value, item) { event ->
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