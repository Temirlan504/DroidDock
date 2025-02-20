package com.example.droiddock

import com.example.droiddock.ui.screens.MainScreen
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.droiddock.viewmodel.MainViewModel
import com.example.droiddock.ui.theme.DroidDockTheme
import com.example.droiddock.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()

        // Hide status bar and navigation bar using WindowInsetsController
        // Check Android version and apply the correct method to hide status and navigation bars
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For API 30 and above (Android 11+)
            window.insetsController?.apply {
                // Hide both status and navigation bars
                hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())

                // But allow bars to reappear when swiped from the edge
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // For API 29 and below (use older method)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContent {
            DroidDockTheme {
                val viewModel: MainViewModel = viewModel()
                val weatherViewModel: WeatherViewModel = viewModel()

                LaunchedEffect(Unit) {
                    weatherViewModel.fetchWeather("Mississauga") // Change city as needed
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        weatherViewModel = weatherViewModel
                    )
                }
            }
        }
    }
}
