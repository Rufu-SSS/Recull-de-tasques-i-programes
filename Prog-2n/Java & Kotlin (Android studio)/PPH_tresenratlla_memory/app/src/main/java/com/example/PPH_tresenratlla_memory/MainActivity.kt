package com.example.PPH_tresenratlla_memory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.PPH_tresenratlla_memory.ui.theme.PPH_MultijocsTheme

enum class Screen {
    MAIN, TRES_EN_RATLLA, MEMORY
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PPH_MultijocsTheme {
                MultijocsApp()
            }
        }
    }
}

@Composable
fun MultijocsApp() {
    var currentScreen by remember { mutableStateOf(Screen.MAIN) }

    when (currentScreen) {
        Screen.MAIN -> MainScreen(
            onNavigateToTresEnRalla = { currentScreen = Screen.TRES_EN_RATLLA },
            onNavigateToMemory = { currentScreen = Screen.MEMORY }
        )

        Screen.TRES_EN_RATLLA -> TresEnRatllaScreen(
            onNavigateBack = { currentScreen = Screen.MAIN }
        )

        Screen.MEMORY -> MemoryScreen(
            onNavigateBack = { currentScreen = Screen.MAIN }
        )
    }
}
