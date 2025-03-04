package com.example.hw7.model.data

import com.example.hw7.model.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("https://api.weatherapi.com/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): WeatherResponse
}