package com.example.speakez.presentation.practice

import com.example.speakez.domain.model.AnalysisResult

data class PracticeScreenState(
    val isLoading: Boolean = false,
    val isRecording: Boolean = false,
    val currentQuestion: String = "",
    val userTranscript: String = "",
    val amplitudes: List<Float> = emptyList(),
    val analysisResult: AnalysisResult? = null,
    val error: String? = null
)
