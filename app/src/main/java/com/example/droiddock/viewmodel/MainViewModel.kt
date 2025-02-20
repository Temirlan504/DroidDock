package com.example.droiddock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    private val _currentTime = MutableStateFlow(getCurrentTime())
    val currentTime = _currentTime.asStateFlow()

    private val _currentDate = MutableStateFlow(getCurrentDate())
    val currentDate = _currentDate.asStateFlow()

    init {
        viewModelScope.launch {
            tickerFlow(1000).collect {
                _currentTime.value = getCurrentTime()
                _currentDate.value = getCurrentDate()
            }
        }
    }

    private fun tickerFlow(intervalMs: Long) = flow {
        while (true) {
            emit(Unit)
            delay(intervalMs)
        }
    }


    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        return timeFormat.format(Date())
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEE dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
