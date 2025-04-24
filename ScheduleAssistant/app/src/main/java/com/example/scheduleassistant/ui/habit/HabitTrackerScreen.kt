package com.example.scheduleassistant.ui.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.data.models.Habit
import com.example.scheduleassistant.ui.theme.MainThColor
import com.example.scheduleassistant.util.NotificationHelper
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

@Composable
fun HabitTrackerScreen(viewModel: HabitViewModel) {
    val habits by viewModel.habits.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val syncError by viewModel.syncError.collectAsState()
    var newHabitName by remember { mutableStateOf("") }
    var newHabitDetails by remember { mutableStateOf("") }
    var newHabitFrequency by remember { mutableStateOf("") }
    var editingHabit by remember { mutableStateOf<Habit?>(null) }
    var editingHabitName by remember { mutableStateOf("") }
    var editingHabitDetails by remember { mutableStateOf("") }
    var editingHabitFrequency by remember { mutableStateOf("") }
    var habitInputError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.loadHabits() }

    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show sync error if exists
            if (syncError != null) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    Text(
                        syncError ?: "",
                        color = MainThColor.ActiveRedOrange,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Text("Habit Tracker", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newHabitName,
                onValueChange = { newHabitName = it },
                label = { Text("Habit Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = newHabitDetails,
                onValueChange = { newHabitDetails = it },
                label = { Text("Details") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = newHabitFrequency,
                onValueChange = { newHabitFrequency = it },
                label = { Text("Frequency") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                if (newHabitName.isBlank()) {
                    habitInputError = "Habit name cannot be empty."
                    return@Button
                }
                if (newHabitFrequency.isNotBlank() && parseFrequencyToMillis(newHabitFrequency) == null) {
                    habitInputError = "Frequency must be in yyyy-MM-dd HH:mm format."
                    return@Button
                }
                habitInputError = null
                viewModel.addHabit(
                    Habit(
                        id = 0,
                        userId = viewModel.userId, // Use actual userId from ViewModel
                        name = newHabitName,
                        details = newHabitDetails,
                        frequency = newHabitFrequency,
                        categoryId = null
                    )
                )
                // Schedule notification for frequency if provided
                if (newHabitFrequency.isNotBlank()) {
                    val triggerTime = parseFrequencyToMillis(newHabitFrequency)
                    if (triggerTime != null && triggerTime > System.currentTimeMillis()) {
                        NotificationHelper.scheduleNotification(
                            context = context,
                            triggerAtMillis = triggerTime,
                            title = "Habit Reminder",
                            content = newHabitName,
                            notificationId = newHabitName.hashCode()
                        )
                    }
                }
                newHabitName = ""
                newHabitDetails = ""
                newHabitFrequency = ""
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Habit")
            }
            if (habitInputError != null) {
                Text(habitInputError!!, color = MainThColor.ActiveRedOrange, modifier = Modifier.padding(top = 4.dp))
            }
            Spacer(Modifier.height(16.dp))
            if (habits.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No habits yet. Add your first habit!", color = MainThColor.TextWhite)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(habits) { habit ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(habit.name, modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                // Show edit dialog
                                editingHabit = habit
                                editingHabitName = habit.name
                                editingHabitDetails = habit.details ?: ""
                                editingHabitFrequency = habit.frequency
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Habit")
                            }
                            IconButton(onClick = { viewModel.deleteHabit(habit) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Habit")
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncHabits() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }

    // Show progress indicator when syncing
    if (isSyncing) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (editingHabit != null) {
        AlertDialog(
            onDismissRequest = { editingHabit = null },
            title = { Text("Edit Habit") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editingHabitName,
                        onValueChange = { editingHabitName = it },
                        label = { Text("Habit Name") }
                    )
                    OutlinedTextField(
                        value = editingHabitDetails,
                        onValueChange = { editingHabitDetails = it },
                        label = { Text("Details") }
                    )
                    OutlinedTextField(
                        value = editingHabitFrequency,
                        onValueChange = { editingHabitFrequency = it },
                        label = { Text("Frequency") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updated = editingHabit!!.copy(
                        name = editingHabitName,
                        details = editingHabitDetails,
                        frequency = editingHabitFrequency
                    )
                    viewModel.updateHabit(updated)
                    editingHabit = null
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { editingHabit = null }) { Text("Cancel") }
            }
        )
    }
}

// Helper to parse frequency string to millis (naive: expects yyyy-MM-dd HH:mm for first reminder)
private fun parseFrequencyToMillis(freqStr: String): Long? {
    return try {
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.parse(freqStr)?.time
    } catch (e: Exception) {
        null
    }
}
