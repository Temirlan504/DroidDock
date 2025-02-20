package com.example.droiddock.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droiddock.BuildConfig
import com.example.droiddock.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.floor

class WeatherViewModel : ViewModel() {
    private val _temperature = MutableStateFlow("Loading...")
    private val _iconUrl = MutableStateFlow("")  // Store the icon URL

    val temperature: StateFlow<String> = _temperature
    val iconUrl: StateFlow<String> = _iconUrl  // Expose the icon URL

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.WEATHER_API_KEY
                val response = RetrofitInstance.api.getWeather(city, apiKey)


                // Convert temperature to int and display without decimal
                val tempInCelsius = floor(response.main.temp).toInt()
                _temperature.value = "$tempInCelsius°C"

                // Get the weather icon code (e.g., "01d") and construct the URL
                val iconCode = response.weather[0].icon
                _iconUrl.value = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

            } catch (e: Exception) {
                _temperature.value = "Error"
                _iconUrl.value = ""  // Clear the icon on error
                Log.e("WeatherViewModel", "Error fetching weather: ${e.message}", e)
            }
        }
    }
}
