package com.example.speakez.data.repository

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.example.speakez.domain.repository.AudioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRepository {

    private var recorder: MediaRecorder? = null
    private var amplitudeUpdateTimer: Timer? = null
    private val amplitudeFlow = MutableStateFlow(0f)

    private fun createRecorder(outputFile: File): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)
            prepare()
        }
    }

    override fun startRecording(outputFile: File) {
        recorder = createRecorder(outputFile).apply {
            start()
            // Start a timer to update the amplitude every 100ms
            amplitudeUpdateTimer = Timer()
            amplitudeUpdateTimer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val maxAmplitude = recorder?.maxAmplitude ?: 0
                    amplitudeFlow.value = maxAmplitude / 32767.0f // Normalize to a 0-1 range
                }
            }, 0, 100)
        }
    }

    override fun stopRecording() {
        amplitudeUpdateTimer?.cancel()
        amplitudeUpdateTimer = null
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        amplitudeFlow.value = 0f
    }

    override fun getAmplitudeFlow(): Flow<Float> {
        return amplitudeFlow
    }
}
