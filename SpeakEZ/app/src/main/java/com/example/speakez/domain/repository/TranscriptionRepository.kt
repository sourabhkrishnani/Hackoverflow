package com.example.speakez.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface TranscriptionRepository {
    fun transcribeAudio(file: File): Flow<String>
}
