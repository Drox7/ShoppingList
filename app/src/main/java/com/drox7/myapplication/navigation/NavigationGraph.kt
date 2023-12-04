package com.drox7.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drox7.myapplication.about_screen.aboutListScreen
import com.drox7.myapplication.main_screen.BottomNavItem
import com.drox7.myapplication.note_list_screen.NoteListScreen
import com.drox7.myapplication.settings_screen.SettingsListScreen
import com.drox7.myapplication.shopping_list_screen.ShoppingListScreen
import com.drox7.myapplication.utils.Routes

@Composable
fun  NavigationGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Routes.SHOPPING_LIST){
        composable(Routes.SHOPPING_LIST){
            ShoppingListScreen()
        }
        composable(Routes.NOTE_LIST){
            NoteListScreen()
        }
        composable(Routes.ABOUT){
            aboutListScreen()
        }
        composable(Routes.SETTINGS){
            SettingsListScreen()
        }
    }

}