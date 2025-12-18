package com.example.speakez.presentation.practice

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.repository.AudioRepository
import com.example.speakez.domain.repository.SimulationRepository
import com.example.speakez.domain.repository.TranscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val application: Application,
    private val audioRepository: AudioRepository,
    private val simulationRepository: SimulationRepository,
    private val transcriptionRepository: TranscriptionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeScreenState())
    val uiState: StateFlow<PracticeScreenState> = _uiState.asStateFlow()

    private val userGoal: String = savedStateHandle.get<String>("userGoal") ?: "General"
    private var outputFile: File? = null

    init {
        fetchNewQuestion()
        collectAmplitude()
    }

    private fun fetchNewQuestion() {
        viewModelScope.launch {
            val question = simulationRepository.getNextQuestion(UserGoal(userGoal, "", emptyList()), "").first()
            _uiState.update { it.copy(currentQuestion = question) }
        }
    }

    private fun collectAmplitude() {
        viewModelScope.launch {
            audioRepository.getAmplitudeFlow().collect { amplitude ->
                // For a smooth waveform, we want a list of recent amplitudes
                val updatedAmplitudes = (_uiState.value.amplitudes + amplitude).takeLast(100)
                _uiState.update { it.copy(amplitudes = updatedAmplitudes) }
            }
        }
    }

    fun onMicButtonPressed() {
        if (_uiState.value.isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }

    private fun startRecording() {
        outputFile = File(application.cacheDir, "recording.m4a")
        _uiState.update { it.copy(isRecording = true, analysisResult = null, userTranscript = "", error = null) }
        outputFile?.let { audioRepository.startRecording(it) }
    }

    private fun stopRecording() {
        _uiState.update { it.copy(isRecording = false) }
        audioRepository.stopRecording()

        viewModelScope.launch {
            outputFile?.let {
                // Show a generic "Processing..." message
                _uiState.update { state -> state.copy(userTranscript = "Processing your answer...") }

                transcriptionRepository.transcribeAudio(it).collect { transcript ->
                    if (transcript.startsWith("Error:")) {
                        _uiState.update { state -> state.copy(error = transcript) }
                    } else {
                        val analysisResult = performLocalAnalysis(transcript)
                        _uiState.update { state -> state.copy(userTranscript = transcript, analysisResult = analysisResult, error = null) }
                    }
                }
            }
        }
    }

    private fun performLocalAnalysis(transcript: String): AnalysisResult {
        // This is a simplified analysis. A real implementation would be more complex.
        val words = transcript.split(Regex("\\s+")).filter { it.isNotBlank() }
        // We don't have the duration here, so WPM is a rough estimate.
        val wpm = words.size * 2 // Assuming an average speaking rate

        return AnalysisResult(
            wpm = wpm,
            fillerWordCount = emptyMap(),
            silenceGaps = emptyList(),
            grammarScore = 0,
            sentiment = "",
            starMethodCheck = false,
            confidence = 0f, clarity = 0f, pace = 0f, content = 0f, grammar = 0f, idealAnswer = ""
        )
    }
}
