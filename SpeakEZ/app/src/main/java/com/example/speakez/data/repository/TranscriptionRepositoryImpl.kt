package com.example.speakez.data.repository

import com.example.speakez.BuildConfig
import com.example.speakez.data.remote.OpenAiApi
import com.example.speakez.domain.repository.TranscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class TranscriptionRepositoryImpl @Inject constructor(
    private val openAiApi: OpenAiApi
) : TranscriptionRepository {

    override fun transcribeAudio(file: File): Flow<String> = flow {
        try {
            // Use the API key from BuildConfig
            val apiKey = "Bearer ${BuildConfig.OPENAI_API_KEY}"
            
            val requestBody = file.asRequestBody("audio/wav".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val model = MultipartBody.Part.createFormData("model", "whisper-1")

            val response = openAiApi.transcribeAudio(apiKey, part, model)
            emit(response.text)
        } catch (e: HttpException) {
            emit("Error: ${e.message()} (Code: ${e.code()}). Check your API key and network connection.")
        } catch (e: Exception) {
            emit("Error: An unexpected error occurred: ${e.message}")
        }
    }
}
