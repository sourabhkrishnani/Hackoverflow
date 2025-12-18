package com.example.speakez.data.repository

import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.repository.SimulationRepository
import com.example.speakez.domain.service.LLMService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimulationRepositoryImpl @Inject constructor(
    private val llmService: LLMService
) : SimulationRepository {

    override fun getNextQuestion(userGoal: UserGoal, currentTranscript: String): Flow<String> {
        return flow {
            // TODO: Implement dynamic question generation based on userGoal and transcript
            emit("Tell me about a time you had to lead a team.")
        }
    }

    override fun analyzeAnswer(transcript: String): Flow<AnalysisResult> {
        return llmService.analyze(transcript)
    }
}
