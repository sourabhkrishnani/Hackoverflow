package com.example.speakez.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun WaveformVisualizer(amplitudes: List<Float>) {
    val barColor = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) { 
        val barWidth = size.width / (2 * amplitudes.size - 1)
        amplitudes.forEachIndexed { index, amplitude ->
            val x = index * 2 * barWidth
            val y = size.height - (amplitude * size.height)
            drawLine(
                color = barColor,
                start = Offset(x, size.height),
                end = Offset(x, y),
                strokeWidth = barWidth,
                cap = StrokeCap.Round
            )
        }
    }
}
