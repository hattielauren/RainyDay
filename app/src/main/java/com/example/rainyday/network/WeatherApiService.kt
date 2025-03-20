package com.example.rainyday.network

import com.example.rainyday.BuildConfig
import com.example.rainyday.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService{
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): Response <WeatherResponse>
}