package com.example.speakez.data.remote.dto

data class OpenApiResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)
