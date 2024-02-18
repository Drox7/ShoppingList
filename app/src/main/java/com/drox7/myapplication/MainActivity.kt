package com.drox7.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.drox7.myapplication.navigation.MainNavigationGraph
import com.drox7.myapplication.ui.theme.ShoppingListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        runBlocking {
//            launch {
//                dataStoreManager.getStringPreferences(
//                    DataStoreManager.SCALE_TEXT_VALUE,
//                    "0.5"
//                ).collect { selectedScaleText ->
//                    AppModule.scaleTextFloat = selectedScaleText.toFloat()
//                }
//            }
//        }

        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigationGraph()
                }
            }
        }
    }
}

//suspend fun getGlobalSettings(dataStoreManager:DataStoreManager) {
//   coroutineScope {
//        launch {
//            dataStoreManager.getStringPreferences(
//                DataStoreManager.SCALE_TEXT_VALUE,
//                "0.5"
//            ).collect { selectedScaleText ->
//                AppModule.scaleTextFloat = selectedScaleText.toFloat()
//            }
//        }
//    }
//}