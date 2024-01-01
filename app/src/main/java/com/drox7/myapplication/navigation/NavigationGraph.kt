package com.drox7.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.drox7.myapplication.about_screen.aboutListScreen
import com.drox7.myapplication.note_list_screen.NoteListScreen
import com.drox7.myapplication.settings_screen.SettingsListScreen
import com.drox7.myapplication.shopping_list_screen.ShoppingListScreen
import com.drox7.myapplication.utils.Routes

@Composable
fun NavigationGraph(navController: NavHostController, onNavigate: (String) -> Unit) {


    NavHost(navController = navController, startDestination = Routes.SHOPPING_LIST) {
        composable(Routes.SHOPPING_LIST) {
            ShoppingListScreen() { route ->
                onNavigate(route)
            }
        }
        composable(Routes.NOTE_LIST) {
            NoteListScreen() { route ->
                onNavigate(route)
            }
        }
        composable(Routes.ABOUT) {
            aboutListScreen()
        }
        composable(Routes.SETTINGS) {
            SettingsListScreen()
        }
    }

}