package com.example.hw7.model.repositories

import android.util.Log
import com.example.hw7.model.core.Either
import com.example.hw7.model.data.ImageService
import com.example.hw7.model.models.ImageResponse
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageService: ImageService) {

    suspend fun getImages(apiKey: String, query: String): Either<Throwable, ImageResponse> {
        return try {
            val response = imageService.getImages(apiKey, query)
            Log.d("ImageRepository", "Fetched images: ${response.hits?.size ?: 0} images")
            Either.Success(response)
        } catch (e: Throwable) {
            Log.e("ImageRepository", "Error fetching images: ${e.message}", e)
            Either.Error(e)
        }
    }
}