package com.example.hw7.model.repositories

import android.util.Log
import com.example.hw7.model.core.Either
import com.example.hw7.model.data.ImageService
import com.example.hw7.model.models.ImageResponse
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageService: ImageService) {

    suspend fun getImages(apiKey: String, query: String): Either<String, ImageResponse> {
        return try {
            val response = imageService.getImages(apiKey, query)
            Either.Success(response)
        } catch (e: Exception) {
            Log.e("ololo", "Error fetching images: ${e.message}")
            Either.Error(e.message ?: "Unknown error")
        }
    }
}