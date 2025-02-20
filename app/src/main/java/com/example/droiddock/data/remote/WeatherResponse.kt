package com.example.droiddock.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,  // Weather conditions list
    @SerializedName("main") val main: Main                 // Main weather data (temp, etc.)
)

data class Weather(
    @SerializedName("icon") val icon: String  // Icon code (e.g., "01d")
)

data class Main(
    @SerializedName("temp") val temp: Double  // Temperature value
)
