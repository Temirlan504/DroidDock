package com.example.droiddock.data.repository

import com.example.droiddock.data.remote.WeatherApi
import com.example.droiddock.data.remote.WeatherResponse

class WeatherRepository(private val api: WeatherApi) {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return api.getWeather(city, apiKey)
    }
}
