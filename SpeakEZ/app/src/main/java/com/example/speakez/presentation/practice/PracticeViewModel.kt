package com.example.speakez.presentation.practice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.repository.AudioRepository
import com.example.speakez.domain.repository.SimulationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val simulationRepository: SimulationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeScreenState())
    val uiState: StateFlow<PracticeScreenState> = _uiState.asStateFlow()

    private val userGoal: String = savedStateHandle.get<String>("userGoal") ?: "General"
    private var recordingStartTime: Long = 0

    init {
        fetchNewQuestion()
        collectTranscript()
    }

    private fun fetchNewQuestion() {
        viewModelScope.launch {
            val question = simulationRepository.getNextQuestion(UserGoal(userGoal, "", emptyList()), "").first()
            _uiState.update { it.copy(currentQuestion = question) }
        }
    }

    private fun collectTranscript() {
        viewModelScope.launch {
            audioRepository.getTranscript().collect { transcript ->
                _uiState.update { it.copy(userTranscript = transcript) }
            }
        }
    }

    fun onMicButtonPressed() {
        if (_uiState.value.isRecording) {
            stopRecording()
        } else if (_uiState.value.analysisResult != null) {
            // Practice Again
            _uiState.update { PracticeScreenState() } // Reset state
            fetchNewQuestion()
        } else {
            startRecording()
        }
    }

    private fun startRecording() {
        _uiState.update { it.copy(isRecording = true, analysisResult = null, userTranscript = "") } // Clear previous results
        recordingStartTime = System.currentTimeMillis()
        audioRepository.startRecording()

        viewModelScope.launch {
            audioRepository.getAudioAmplitude().collect { amplitude ->
                _uiState.update { it.copy(amplitudes = it.amplitudes + amplitude) }
            }
        }
    }

    private fun stopRecording() {
        _uiState.update { it.copy(isRecording = false) }
        audioRepository.stopRecording()
        
        val recordingTimeSeconds = (System.currentTimeMillis() - recordingStartTime) / 1000.0
        
        viewModelScope.launch {
            // We use the final transcript from the state for analysis
            val finalTranscript = uiState.value.userTranscript
            val analysisResult = performLocalAnalysis(finalTranscript, recordingTimeSeconds)
            _uiState.update { it.copy(analysisResult = analysisResult, error = null) }
        }
    }

    private fun performLocalAnalysis(transcript: String, durationSeconds: Double): AnalysisResult {
        val words = transcript.split(Regex("\\s+")).filter { it.isNotBlank() }
        val wpm = if (durationSeconds > 0) ((words.size / durationSeconds) * 60).toInt() else 0

        val fillerWords = setOf("um", "uh", "like", "you know", "ah", "so", "basically")
        val fillerWordCount = words.groupingBy { it.lowercase().replace(Regex("[^a-z]"), "") }
            .eachCount()
            .filter { fillerWords.contains(it.key) }

        // Create a placeholder result, as we don't have a real LLM call yet
        return AnalysisResult(
            wpm = wpm,
            fillerWordCount = fillerWordCount,
            // These are still placeholders until we connect the LLM
            silenceGaps = emptyList(), 
            grammarScore = 80, 
            sentiment = "Neutral",
            starMethodCheck = false,
            confidence = 0.0f,
            clarity = 0.0f,
            pace = 0.0f,
            content = 0.0f,
            grammar = 0.0f,
            idealAnswer = ""
        )
    }
}
