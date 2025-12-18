package com.example.speakez.data.remote.dto

data class OpenApiRequest(
    val model: String,
    val prompt: String,
    val max_tokens: Int
)
