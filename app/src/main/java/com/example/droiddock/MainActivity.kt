package com.example.droiddock

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import com.example.droiddock.ui.theme.DroidDockTheme
import com.example.droiddock.ui.theme.OswaldFontFamily
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // State to hold the current time
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var currentDate by remember { mutableStateOf(getCurrentDate()) }

    // Update the time every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            currentDate = getCurrentDate()
            delay(1000) // Update every second
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Large Time Text
            Text(
                text = currentTime,
                fontFamily = OswaldFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 220.sp,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 70.dp)
                    .align(Alignment.CenterVertically)
                    .graphicsLayer(
                        scaleY = 1.5f // Stretch text vertically by 1.5 times (you can adjust this value)
                    )
            )

            // Date & Temperature
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .align(Alignment.Top)
                    .padding(top = 35.dp, start = 32.dp)
            ) {
                Text(
                    text = currentDate,
                    fontFamily = OswaldFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 42.sp
                )
                Text(
                    text = "-15°C",
                    fontFamily = OswaldFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 32.sp
                )
            }
        }
    }
}

// Function to get the current time
fun getCurrentTime(): String {
    val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    return timeFormat.format(Date())
}

// Function to get the current date
fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("EEE dd", Locale.getDefault())
    return dateFormat.format(Date())
}