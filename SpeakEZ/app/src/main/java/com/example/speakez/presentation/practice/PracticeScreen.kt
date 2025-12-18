package com.example.speakez.presentation.practice

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.speakez.presentation.composables.StandardButton
import com.example.speakez.presentation.composables.StatCard
import com.example.speakez.presentation.composables.WaveformVisualizer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PracticeScreen(
    viewModel: PracticeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    DisposableEffect(Unit) {
        permissionState.launchPermissionRequest()
        onDispose { }
    }

    if (permissionState.status.isGranted) {
        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text("Practice Mode") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (uiState.analysisResult == null) {
                    // Recording and Live-Transcription View
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Question:", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(uiState.currentQuestion, style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                    Text(uiState.userTranscript, modifier = Modifier.padding(vertical = 24.dp))
                    WaveformVisualizer(amplitudes = uiState.amplitudes)
                } else {
                    // Analysis View
                    uiState.analysisResult?.let { result ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Your Analysis", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatCard(label = "WPM", value = result.wpm.toString())
                                StatCard(label = "Fillers", value = result.fillerWordCount.values.sum().toString())
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            StandardButton(
                                text = "Show Ideal Answer",
                                onClick = { /* TODO */ }
                            )
                        }
                    }
                }

                StandardButton(
                    text = if (uiState.isRecording) "Stop" else if (uiState.analysisResult != null) "Practice Again" else "Record",
                    onClick = { viewModel.onMicButtonPressed() }
                )
            }
        }
    } else {
        // Permission Denied View
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Microphone permission is required to use this feature.")
            Spacer(modifier = Modifier.height(16.dp))
            StandardButton(
                text = "Grant Permission",
                onClick = { permissionState.launchPermissionRequest() }
            )
        }
    }
}
