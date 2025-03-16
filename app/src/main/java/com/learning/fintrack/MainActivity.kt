package com.learning.fintrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.learning.fintrack.presentation.ui.addAccount.AddAccountScreen
import com.learning.fintrack.presentation.ui.home.HomeScreen
import com.learning.fintrack.presentation.ui.theme.FinTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinTrackTheme {
                HomeScreen()
            }
        }
    }
}
