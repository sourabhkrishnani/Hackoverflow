package com.example.speakez.domain.repository

import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import kotlinx.coroutines.flow.Flow

interface SimulationRepository {
    fun getNextQuestion(userGoal: UserGoal, currentTranscript: String): Flow<String>
    fun analyzeAnswer(transcript: String): Flow<AnalysisResult>
}
