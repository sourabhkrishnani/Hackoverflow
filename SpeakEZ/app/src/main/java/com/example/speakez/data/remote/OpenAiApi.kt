package com.example.speakez.data.remote

import com.example.speakez.data.remote.dto.OpenApiRequest
import com.example.speakez.data.remote.dto.OpenApiResponse
import com.example.speakez.data.remote.dto.WhisperResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OpenAiApi {
    @POST("v1/completions")
    suspend fun getCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: OpenApiRequest
    ): OpenApiResponse

    @Multipart
    @POST("v1/audio/transcriptions")
    suspend fun transcribeAudio(
        @Header("Authorization") apiKey: String,
        @Part file: MultipartBody.Part,
        @Part model: MultipartBody.Part
    ): WhisperResponse
}
