package com.example.scheduleassistant.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.data.models.HistoryEntry
import com.example.scheduleassistant.ui.theme.MainThColor
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val historyEntries by viewModel.historyEntries.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadHistoryEntries() }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("History", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            // --- Filtering State ---
            var featureFilter by remember { mutableStateOf("all") }
            var dateRangeDays by remember { mutableStateOf(7) }
            val now = LocalDate.now()
            var customStartDate by remember { mutableStateOf<LocalDate?>(null) }
            var customEndDate by remember { mutableStateOf<LocalDate?>(null) }
            var showStartPicker by remember { mutableStateOf(false) }
            var showEndPicker by remember { mutableStateOf(false) }
            val useCustomRange = customStartDate != null && customEndDate != null
            val filteredEntries = historyEntries.filter {
                (featureFilter == "all" || it.feature == featureFilter) &&
                (
                    if (useCustomRange) {
                        val d = it.completedOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        d >= customStartDate && d <= customEndDate
                    } else {
                        it.completedOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() >= now.minusDays(dateRangeDays.toLong() - 1)
                    }
                )
            }
            // --- Analytics Section ---
            val totalCount = filteredEntries.size
            val taskCount = filteredEntries.count { it.feature == "task" }
            val habitCount = filteredEntries.count { it.feature == "habit" }
            val scheduleCount = filteredEntries.count { it.feature == "schedule" }
            val streak = calculateStreak(filteredEntries)
            val chartData = (0 until dateRangeDays).map { i ->
                val date = now.minusDays((dateRangeDays - 1 - i).toLong())
                val count = filteredEntries.count { it.completedOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() == date }
                date to count
            }
            val chartEntries = entryModelOf(*chartData.mapIndexed { idx, (_, count) -> idx to count }.toTypedArray())
            // --- Filter UI ---
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Show:", color = MainThColor.TextWhite)
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = featureFilter == "all", onClick = { featureFilter = "all" }, label = { Text("All") })
                Spacer(Modifier.width(4.dp))
                FilterChip(selected = featureFilter == "task", onClick = { featureFilter = "task" }, label = { Text("Tasks") })
                Spacer(Modifier.width(4.dp))
                FilterChip(selected = featureFilter == "habit", onClick = { featureFilter = "habit" }, label = { Text("Habits") })
                Spacer(Modifier.width(4.dp))
                FilterChip(selected = featureFilter == "schedule", onClick = { featureFilter = "schedule" }, label = { Text("Schedules") })
                Spacer(Modifier.width(16.dp))
                Text("Range:", color = MainThColor.TextWhite)
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = dateRangeDays == 7 && !useCustomRange, onClick = { dateRangeDays = 7; customStartDate = null; customEndDate = null }, label = { Text("7d") })
                Spacer(Modifier.width(4.dp))
                FilterChip(selected = dateRangeDays == 30 && !useCustomRange, onClick = { dateRangeDays = 30; customStartDate = null; customEndDate = null }, label = { Text("30d") })
                Spacer(Modifier.width(4.dp))
                FilterChip(selected = useCustomRange, onClick = { showStartPicker = true }, label = { Text("Custom") })
            }
            if (useCustomRange) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("From: ${customStartDate}", color = MainThColor.TextWhite)
                    Spacer(Modifier.width(8.dp))
                    Text("To: ${customEndDate}", color = MainThColor.TextWhite)
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { customStartDate = null; customEndDate = null }) { Text("Clear", color = MainThColor.ActiveRedOrange) }
                }
            }
            if (showStartPicker) {
                // TODO: Update to correct DatePickerDialog API for Compose Material3
                // DatePickerDialog(onDismissRequest = { showStartPicker = false }, onDateChange = { date ->
                //     customStartDate = date
                //     showStartPicker = false
                //     showEndPicker = true
                // })
            }
            if (showEndPicker) {
                // TODO: Update to correct DatePickerDialog API for Compose Material3
                // DatePickerDialog(onDismissRequest = { showEndPicker = false }, onDateChange = { date ->
                //     customEndDate = date
                //     showEndPicker = false
                // })
            }
            Spacer(Modifier.height(12.dp))
            // --- Chart ---
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(Modifier.padding(8.dp)) {
                    Text("Completions (Line Chart)", color = MainThColor.TextWhite)
                    Chart(
                        chart = lineChart(),
                        model = chartEntries
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            // --- Analytics ---
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(Modifier.padding(8.dp)) {
                    Text("Total Completions: $totalCount", color = MainThColor.TextWhite)
                    Text("Tasks: $taskCount  Habits: $habitCount  Schedules: $scheduleCount", color = MainThColor.TextWhite)
                    Text("Current Streak: $streak days", color = MainThColor.TextWhite)
                }
            }
            Spacer(Modifier.height(16.dp))
            // --- History List ---
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredEntries) { entry ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(entry.feature, fontSize = 18.sp, color = MainThColor.TextWhite)
                            Text("${entry.completedOn}", color = MainThColor.TextGrey)
                            entry.javaClass.kotlin.members.find { it.name == "details" }?.call(entry)?.let {
                                Text(it.toString(), color = MainThColor.TextGrey)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncHistoryEntries() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}

// --- Analytics Helper ---
fun calculateStreak(entries: List<HistoryEntry>): Int {
    val today = LocalDate.now()
    val days = entries.map { it.completedOn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() }.toSet()
    var streak = 0
    var current = today
    while (days.contains(current)) {
        streak++
        current = current.minusDays(1)
    }
    return streak
}
