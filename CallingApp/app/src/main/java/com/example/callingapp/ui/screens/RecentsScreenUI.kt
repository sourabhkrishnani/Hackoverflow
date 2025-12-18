package com.example.moderncaller.ui.screens

import android.provider.CallLog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moderncaller.viewmodel.CallLogEntry
import com.example.moderncaller.viewmodel.RecentsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecentsScreen(recentsViewModel: RecentsViewModel = viewModel()) {
    val uiState by recentsViewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(uiState.callLogs) { log ->
                CallLogItem(log)
            }
        }
    }
}

@Composable
fun CallLogItem(log: CallLogEntry) {
    val icon = when (log.type) {
        CallLog.Calls.OUTGOING_TYPE -> Icons.Default.CallMade
        CallLog.Calls.INCOMING_TYPE -> Icons.Default.CallReceived
        CallLog.Calls.MISSED_TYPE -> Icons.Default.CallMissed
        else -> Icons.Default.CallMade
    }
    val color = when (log.type) {
        CallLog.Calls.MISSED_TYPE -> Color.Red
        else -> Color.Unspecified
    }

    ListItem(
        headlineContent = { Text(log.name ?: log.number) },
        supportingContent = { Text(formatDate(log.date)) },
        leadingContent = { Icon(icon, contentDescription = null, tint = color) }
    )
}

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    return format.format(date)
}
