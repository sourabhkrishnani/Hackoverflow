package com.example.speakez.data.repository

import com.example.speakez.data.remote.OpenAiApi
import com.example.speakez.domain.repository.TranscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class TranscriptionRepositoryImpl @Inject constructor(
    private val openAiApi: OpenAiApi
) : TranscriptionRepository {

    override fun transcribeAudio(file: File): Flow<String> = flow {
        val requestBody = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val model = MultipartBody.Part.createFormData("model", "whisper-1")

        // You need to replace "YOUR_API_KEY" with your actual OpenAI API key
        val response = openAiApi.transcribeAudio("Bearer YOUR_API_KEY", part, model)
        
        emit(response.text)
    }
}
