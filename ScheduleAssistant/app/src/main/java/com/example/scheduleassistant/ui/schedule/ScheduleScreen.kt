package com.example.scheduleassistant.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.data.models.ScheduleEntry
import com.example.scheduleassistant.ui.theme.MainThColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel) {
    val scheduleEntries by viewModel.scheduleEntries.collectAsState()
    var newTitle by remember { mutableStateOf("") }
    var newStartTime by remember { mutableStateOf("") }
    var newEndTime by remember { mutableStateOf("") }
    var newDetails by remember { mutableStateOf("") }
    var editingEntry by remember { mutableStateOf<ScheduleEntry?>(null) }
    var editingTitle by remember { mutableStateOf("") }
    var editingStartTime by remember { mutableStateOf("") }
    var editingEndTime by remember { mutableStateOf("") }
    var editingDetails by remember { mutableStateOf("") }
    var scheduleInputError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.loadScheduleEntries() }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Daily Schedule", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newStartTime,
                onValueChange = { newStartTime = it },
                label = { Text("Start Time (yyyy-MM-dd HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newEndTime,
                onValueChange = { newEndTime = it },
                label = { Text("End Time (yyyy-MM-dd HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newDetails,
                onValueChange = { newDetails = it },
                label = { Text("Details") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            if (scheduleInputError != null) {
                Text(scheduleInputError!!, color = MainThColor.ActiveRedOrange, modifier = Modifier.padding(top = 4.dp))
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (newTitle.isBlank()) {
                    scheduleInputError = "Title cannot be empty."
                    return@Button
                }
                val startMillis = parseTimeToMillis(newStartTime)
                val endMillis = parseTimeToMillis(newEndTime)
                if (startMillis == null || endMillis == null) {
                    scheduleInputError = "Start and End Time must be in yyyy-MM-dd HH:mm format."
                    return@Button
                }
                scheduleInputError = null
                viewModel.addScheduleEntry(
                    ScheduleEntry(
                        id = 0,
                        userId = viewModel.userId,
                        title = newTitle,
                        details = newDetails,
                        startTime = startMillis,
                        endTime = endMillis,
                        date = Date(startMillis)
                    )
                )
                newTitle = ""
                newStartTime = ""
                newEndTime = ""
                newDetails = ""
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Entry")
            }
            Spacer(Modifier.height(16.dp))
            if (scheduleEntries.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No schedule entries yet. Add your first one!", color = MainThColor.TextGrey)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(scheduleEntries) { entry ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(Modifier.padding(8.dp)) {
                                Text(entry.title, fontSize = 18.sp, color = MainThColor.TextWhite)
                                Text("Details: ${entry.details ?: "N/A"}", color = MainThColor.TextGrey)
                                Text("Start: ${formatMillisToTime(entry.startTime)}", color = MainThColor.TextGrey)
                                Text("End: ${formatMillisToTime(entry.endTime)}", color = MainThColor.TextGrey)
                                Text("Date: ${entry.date}", color = MainThColor.TextGrey)
                            }
                        }
                    }
                }
            }
        }
    }

    if (editingEntry != null) {
        AlertDialog(
            onDismissRequest = { editingEntry = null },
            title = { Text("Edit Schedule Entry") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editingTitle,
                        onValueChange = { editingTitle = it },
                        label = { Text("Title") }
                    )
                    OutlinedTextField(
                        value = editingStartTime,
                        onValueChange = { editingStartTime = it },
                        label = { Text("Start Time (yyyy-MM-dd HH:mm)") }
                    )
                    OutlinedTextField(
                        value = editingEndTime,
                        onValueChange = { editingEndTime = it },
                        label = { Text("End Time (yyyy-MM-dd HH:mm)") }
                    )
                    OutlinedTextField(
                        value = editingDetails,
                        onValueChange = { editingDetails = it },
                        label = { Text("Details") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updated = editingEntry!!.copy(
                        title = editingTitle,
                        startTime = parseTimeToMillis(editingStartTime)!!,
                        endTime = parseTimeToMillis(editingEndTime)!!,
                        details = editingDetails
                    )
                    viewModel.updateScheduleEntry(updated)
                    editingEntry = null
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { editingEntry = null }) { Text("Cancel") }
            }
        )
    }
}

fun parseTimeToMillis(timeStr: String): Long? {
    return try {
        SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr)?.time
    } catch (e: Exception) {
        null
    }
}

fun formatMillisToTime(millis: Long): String {
    return try {
        SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(millis))
    } catch (e: Exception) {
        "Invalid time"
    }
}
