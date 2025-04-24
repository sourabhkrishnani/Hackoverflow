package com.example.scheduleassistant.ui.task

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
import com.example.scheduleassistant.data.models.Task
import com.example.scheduleassistant.ui.theme.MainThColor
import com.example.scheduleassistant.util.NotificationHelper
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete

@Composable
fun TaskTrackerScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskName by remember { mutableStateOf("") }
    var newTaskDetails by remember { mutableStateOf("") }
    var newTaskFrequency by remember { mutableStateOf("") }
    var newTaskCategoryId by remember { mutableStateOf("") }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var editingTaskName by remember { mutableStateOf("") }
    var editingTaskDetails by remember { mutableStateOf("") }
    var editingTaskFrequency by remember { mutableStateOf("") }
    var editingTaskCategoryId by remember { mutableStateOf("") }
    var taskInputError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.loadTasks() }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Task Tracker", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newTaskName,
                onValueChange = { newTaskName = it },
                label = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newTaskDetails,
                onValueChange = { newTaskDetails = it },
                label = { Text("Details") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newTaskFrequency,
                onValueChange = { newTaskFrequency = it },
                label = { Text("Frequency") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newTaskCategoryId,
                onValueChange = { newTaskCategoryId = it },
                label = { Text("Category ID (optional)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            if (taskInputError != null) {
                Text(taskInputError!!, color = MainThColor.ActiveRedOrange, modifier = Modifier.padding(top = 4.dp))
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (newTaskName.isBlank()) {
                    taskInputError = "Task name cannot be empty."
                    return@Button
                }
                if (newTaskFrequency.isBlank()) {
                    taskInputError = "Frequency cannot be empty."
                    return@Button
                }
                taskInputError = null
                viewModel.addTask(
                    Task(
                        id = 0,
                        userId = viewModel.userId,
                        name = newTaskName,
                        details = newTaskDetails,
                        frequency = newTaskFrequency,
                        categoryId = newTaskCategoryId.toIntOrNull(),
                        createdAt = Date()
                    )
                )
                newTaskName = ""
                newTaskDetails = ""
                newTaskFrequency = ""
                newTaskCategoryId = ""
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Task")
            }
            Spacer(Modifier.height(16.dp))
            if (tasks.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tasks yet. Add your first task!", color = MainThColor.TextGrey)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(tasks) { task ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(Modifier.padding(8.dp)) {
                                Text(task.name, fontSize = 18.sp, color = MainThColor.TextWhite)
                                Text("Details: ${task.details ?: "N/A"}", color = MainThColor.TextGrey)
                                Text("Frequency: ${task.frequency}", color = MainThColor.TextGrey)
                                Text("Category ID: ${task.categoryId ?: "N/A"}", color = MainThColor.TextGrey)
                                Text("Created: ${task.createdAt}", color = MainThColor.TextGrey)
                                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        editingTask = task
                                        editingTaskName = task.name
                                        editingTaskDetails = task.details ?: ""
                                        editingTaskFrequency = task.frequency
                                        editingTaskCategoryId = task.categoryId?.toString() ?: ""
                                    }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Edit Task")
                                    }
                                    IconButton(onClick = { viewModel.deleteTask(task) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete Task")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (editingTask != null) {
        AlertDialog(
            onDismissRequest = { editingTask = null },
            title = { Text("Edit Task") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editingTaskName,
                        onValueChange = { editingTaskName = it },
                        label = { Text("Task Name") }
                    )
                    OutlinedTextField(
                        value = editingTaskDetails,
                        onValueChange = { editingTaskDetails = it },
                        label = { Text("Details") }
                    )
                    OutlinedTextField(
                        value = editingTaskFrequency,
                        onValueChange = { editingTaskFrequency = it },
                        label = { Text("Frequency") }
                    )
                    OutlinedTextField(
                        value = editingTaskCategoryId,
                        onValueChange = { editingTaskCategoryId = it },
                        label = { Text("Category ID") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updated = editingTask!!.copy(
                        name = editingTaskName,
                        details = editingTaskDetails,
                        frequency = editingTaskFrequency,
                        categoryId = editingTaskCategoryId.toIntOrNull()
                    )
                    viewModel.updateTask(updated)
                    editingTask = null
                }) { Text("Save") }
            },
            dismissButton = {
                Button(onClick = { editingTask = null }) { Text("Cancel") }
            }
        )
    }
}
