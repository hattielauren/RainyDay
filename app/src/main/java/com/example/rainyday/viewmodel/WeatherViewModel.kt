package com.example.rainyday.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rainyday.BuildConfig
import com.example.rainyday.model.WeatherResponse
import com.example.rainyday.network.WeatherApiClient
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    private val weatherApi = WeatherApiClient.weatherApi

    private val lat = 44.9537
    private val lon = -93.0900

    fun getCurrentWeather() {
        viewModelScope.launch {
            try {
                val response = weatherApi.getCurrentWeather(lat, lon, BuildConfig.API_KEY)

                _weatherData.postValue(response.body())
                Log.d("WeatherAPI", "Weather Data: ${response.body()}")
            } catch (e: Exception) {
                Log.e("WeatherAPI", "Error: ${e.message}")
                e.printStackTrace()
                _weatherData.postValue(null) // Handle errors gracefully
            }
        }
    }
}