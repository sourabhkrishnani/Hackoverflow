package com.example.speakez.domain.repository

import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    fun startRecording(outputFile: java.io.File)
    fun stopRecording()
    fun getAmplitudeFlow(): Flow<Float>
}
