package com.example.droiddock.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.droiddock.ui.theme.OswaldFontFamily
import com.example.droiddock.viewmodel.MainViewModel
import com.example.droiddock.viewmodel.WeatherViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    val currentTime by viewModel.currentTime.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val temperature by weatherViewModel.temperature.collectAsState()
    val weatherIconUrl by weatherViewModel.iconUrl.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentTime,
                fontFamily = OswaldFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 220.sp,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 70.dp)
                    .align(Alignment.CenterVertically)
                    .graphicsLayer(scaleY = 1.5f) // Stretch text
            )

            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .align(Alignment.Top)
                    .padding(top = 35.dp, start = 32.dp)
            ) {
                // Date and day
                Text(
                    text = currentDate,
                    fontFamily = OswaldFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 42.sp
                )

                // Display Weather
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Weather Icon and Text
                    Image(
                        painter = rememberAsyncImagePainter(weatherIconUrl),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer(scaleX = 2.0f, scaleY = 2.0f), // Scale because image has internal padding
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = temperature,
                        fontFamily = OswaldFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 32.sp
                    )

                    // "Refresh" button
                    IconButton(onClick = { weatherViewModel.fetchWeather("Mississauga") }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Weather",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
    }
}
