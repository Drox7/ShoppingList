package com.drox7.myapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drox7.myapplication.add_item_screen.AddItemScreen
import com.drox7.myapplication.main_screen.MainScreen
import com.drox7.myapplication.new_note_screen.NewNoteScreen
import com.drox7.myapplication.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.background(colorScheme.background),
        navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.ADD_ITEM + "/{listId}") {entry ->
            //Log.d("MyLog", "List id ${entry.arguments?.getString("listId")}")

            AddItemScreen()
        }
        composable(Routes.NEW_NOTE +"/{noteId}") {
            NewNoteScreen(){
                navController.popBackStack()
            }
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)

        }
    }

}