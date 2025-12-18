package com.example.speakez.domain.repository

import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    fun startRecording()
    fun stopRecording()
    fun getAudioAmplitude(): Flow<Float>
    fun getTranscript(): Flow<String>
}
