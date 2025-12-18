package com.example.speakez.domain.model

data class AnalysisResult(
    val wpm: Int,
    val silenceGaps: List<Float>,
    val grammarScore: Int,
    val sentiment: String,
    val fillerWordCount: Map<String, Int>,
    val starMethodCheck: Boolean,
    val confidence: Float,
    val clarity: Float,
    val pace: Float,
    val content: Float,
    val grammar: Float,
    val idealAnswer: String
)
