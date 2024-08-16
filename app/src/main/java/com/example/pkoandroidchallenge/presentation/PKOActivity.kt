package com.example.pkoandroidchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.pkoandroidchallenge.presentation.navigation.PKONavigation
import com.example.pkoandroidchallenge.presentation.theme.PKOAndroidChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PKOActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PKOAndroidChallengeTheme {
                val navController = rememberNavController()
                Scaffold {
                    PKONavigation(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        navController = navController,
                    )
                }
            }
        }
    }
}
