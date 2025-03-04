package com.example.hw7.model.repositories

import android.util.Log
import com.example.hw7.model.core.Either
import com.example.hw7.model.data.WeatherService
import com.example.hw7.model.models.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(private  val weatherService: WeatherService) {

    suspend fun getWeather(apiKey: String, query: String): Either<Throwable, WeatherResponse> {
        return try {
            val response = weatherService.getWeather(apiKey, query)
            Log.e("ololo", "Response: $response")
            Either.Success(response)
        } catch (e: Throwable) {
            Log.e("ololo", "Error: $e")
            Either.Error(e)
        }
    }
}