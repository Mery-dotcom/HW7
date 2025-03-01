package com.example.hw7.model.data

import com.example.hw7.model.models.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): ImageResponse
}