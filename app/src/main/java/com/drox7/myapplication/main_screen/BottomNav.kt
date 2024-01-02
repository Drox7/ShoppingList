package com.drox7.myapplication.main_screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.drox7.myapplication.ui.theme.GrayLight


@Composable
fun BottomNav(
    titleColor: Color,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val listItems = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.SettingsItem,
        BottomNavItem.AboutItem,
    )
    BottomNavigation(backgroundColor = colorScheme.onPrimary) {
        listItems.forEach { bottomNavItem ->

            BottomNavigationItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                          onNavigate(bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = bottomNavItem.iconId
                        ),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = bottomNavItem.title)
                },
                selectedContentColor = titleColor,
                unselectedContentColor = GrayLight,
                alwaysShowLabel = false
            )
        }
    }
}

//@Preview
//@Composable
//fun PrevBottomNav() {
//    BottomNav()
//}