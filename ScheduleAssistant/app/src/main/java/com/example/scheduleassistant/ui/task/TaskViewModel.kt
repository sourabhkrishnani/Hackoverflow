package com.example.scheduleassistant.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.Task
import com.example.scheduleassistant.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {
    val userId: String = repo.userId // Expose userId for use in UI
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repo.getTasks()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repo.addTask(task)
            loadTasks()
        }
    }

    fun syncTasks() {
        viewModelScope.launch {
            repo.syncTasks()
            loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repo.updateTask(task)
            loadTasks()
        }
    }

    fun logTaskCompletion(task: Task) {
        viewModelScope.launch {
            repo.logTaskCompletion(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repo.deleteTask(task)
            loadTasks()
        }
    }
}
