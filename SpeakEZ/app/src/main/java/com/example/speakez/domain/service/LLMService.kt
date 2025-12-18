package com.example.speakez.domain.service

import com.example.speakez.domain.model.AnalysisResult
import kotlinx.coroutines.flow.Flow

interface LLMService {
    fun analyze(text: String): Flow<AnalysisResult>
}
