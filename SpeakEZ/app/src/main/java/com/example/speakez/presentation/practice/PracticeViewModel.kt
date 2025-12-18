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
    private var recordingStartTime: Long = 0
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
                _uiState.update { it.copy(amplitudes = it.amplitudes + amplitude) }
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
        outputFile = File(application.cacheDir, "recording.wav")
        _uiState.update { it.copy(isRecording = true, analysisResult = null, userTranscript = "") }
        recordingStartTime = System.currentTimeMillis()
        outputFile?.let { audioRepository.startRecording(it) }
    }

    private fun stopRecording() {
        _uiState.update { it.copy(isRecording = false) }
        audioRepository.stopRecording()

        viewModelScope.launch {
            outputFile?.let {
                transcriptionRepository.transcribeAudio(it).collect { transcript ->
                    _uiState.update { state -> state.copy(userTranscript = transcript) }
                    val recordingTimeSeconds = (System.currentTimeMillis() - recordingStartTime) / 1000.0
                    val analysisResult = performLocalAnalysis(transcript, recordingTimeSeconds)
                    _uiState.update { state -> state.copy(analysisResult = analysisResult, error = null) }
                }
            }
        }
    }

    private fun performLocalAnalysis(transcript: String, durationSeconds: Double): AnalysisResult {
        val words = transcript.split(Regex("\\s+")).filter { it.isNotBlank() }
        val wpm = if (durationSeconds > 1) ((words.size / durationSeconds) * 60).toInt() else 0

        return AnalysisResult(
            wpm = wpm,
            fillerWordCount = emptyMap(), // Will be implemented later
            silenceGaps = emptyList(),
            grammarScore = 0,
            sentiment = "",
            starMethodCheck = false,
            confidence = 0f, clarity = 0f, pace = 0f, content = 0f, grammar = 0f, idealAnswer = ""
        )
    }
}
