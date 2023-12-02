package com.drox7.myapplication.main_screen

import android.annotation.SuppressLint
//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.BlueLight


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold(bottomBar = {
        BottomNav()
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { }, backgroundColor = BlueLight) {
               Icon(painter = painterResource(
                   id = R.drawable.add_icon
               ),
                   contentDescription ="Add",
                  tint = Color.White
               )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { 
        
    }
}