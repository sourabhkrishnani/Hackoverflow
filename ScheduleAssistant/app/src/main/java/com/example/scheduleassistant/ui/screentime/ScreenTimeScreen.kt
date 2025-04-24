package com.example.scheduleassistant.ui.screentime

import android.app.usage.UsageStats
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.data.models.ScreenTimeEntry
import com.example.scheduleassistant.ui.screentime.UsageStatsUtil
import com.example.scheduleassistant.ui.theme.MainThColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ScreenTimeScreen(viewModel: ScreenTimeViewModel) {
    val screenTimeEntries by viewModel.screenTimeEntries.collectAsState()
    val context = LocalContext.current
    var usageStatsList by remember { mutableStateOf<List<UsageStats>>(emptyList()) }
    var showUsageAccessDialog by remember { mutableStateOf(false) }
    var newAppName by remember { mutableStateOf("") }
    var newDurationMinutes by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (!UsageStatsUtil.hasUsageStatsPermission(context)) {
            showUsageAccessDialog = true
        } else {
            usageStatsList = UsageStatsUtil.getUsageStatsList(context)
        }
        viewModel.loadScreenTimeEntries()
    }

    if (showUsageAccessDialog) {
        AlertDialog(
            onDismissRequest = { showUsageAccessDialog = false },
            title = { Text("Usage Access Needed") },
            text = { Text("To show app usage duration, please grant Usage Access permission in settings.") },
            confirmButton = {
                Button(onClick = {
                    UsageStatsUtil.launchUsageAccessSettings(context)
                    showUsageAccessDialog = false
                }) { Text("Open Settings") }
            },
            dismissButton = {
                Button(onClick = { showUsageAccessDialog = false }) { Text("Cancel") }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Screen Time Tracker", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            Text("Recent App Usage (Today):", color = MainThColor.TextWhite)
            LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                items(usageStatsList.sortedByDescending { it.totalTimeInForeground }) { stat ->
                    val appName = stat.packageName
                    val durationMin = stat.totalTimeInForeground / 1000 / 60
                    if (durationMin > 0) {
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(Modifier.padding(8.dp)) {
                                Text("App: $appName", color = MainThColor.TextWhite)
                                Text("Usage: $durationMin minutes", color = MainThColor.TextGrey)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Manual Entries:", color = MainThColor.TextWhite)
            LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                items(screenTimeEntries) { entry ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text("App: ${entry.appName}", color = MainThColor.TextWhite)
                            Text("Duration: ${entry.durationMinutes} minutes", color = MainThColor.TextGrey)
                            Text("Date: ${entry.date}", color = MainThColor.TextGrey)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newAppName,
                onValueChange = { newAppName = it },
                label = { Text("App Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newDurationMinutes,
                onValueChange = { newDurationMinutes = it },
                label = { Text("Duration (minutes)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newDate,
                onValueChange = { newDate = it },
                label = { Text("Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Button(onClick = {
                if (newAppName.isNotBlank() && newDurationMinutes.isNotBlank()) {
                    val parsedDate = try {
                        SimpleDateFormat("yyyy-MM-dd").parse(newDate)
                    } catch (e: Exception) {
                        null
                    }
                    viewModel.addScreenTimeEntry(
                        ScreenTimeEntry(
                            id = 0,
                            userId = viewModel.userId,
                            appName = newAppName,
                            durationMinutes = newDurationMinutes.toIntOrNull() ?: 0,
                            date = parsedDate ?: Date()
                        )
                    )
                    newAppName = ""
                    newDurationMinutes = ""
                    newDate = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Entry")
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncScreenTimeEntries() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}
