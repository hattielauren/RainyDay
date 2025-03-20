package com.example.rainyday.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse (
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("name") val cityName: String
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike:Double,
    @SerialName("temp_min") val tempMin:Double,
    @SerialName("temp_max") val tempMax:Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int
)

@Serializable
data class Weather(
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)