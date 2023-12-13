package com.drox7.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drox7.myapplication.about_screen.aboutListScreen
import com.drox7.myapplication.add_item_screen.AddItemScreen
import com.drox7.myapplication.main_screen.BottomNavItem
import com.drox7.myapplication.main_screen.MainScreen
import com.drox7.myapplication.new_note_screen.NewNoteScreen
import com.drox7.myapplication.note_list_screen.NoteListScreen
import com.drox7.myapplication.settings_screen.SettingsListScreen
import com.drox7.myapplication.shopping_list_screen.ShoppingListScreen
import com.drox7.myapplication.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.ADD_ITEM) {
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE) {
            NewNoteScreen()
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)

        }
    }

}