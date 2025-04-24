package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.Task
import com.example.scheduleassistant.data.models.HistoryEntry
import com.example.scheduleassistant.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.scheduleassistant.BuildConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.Date
import android.content.Context

class TaskRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val _userId: String,
    private val token: String
) {
    val userId: String
        get() = _userId

    suspend fun getTasks(): List<Task> = withContext(Dispatchers.IO) {
        db.taskDao().getAll().filter { it.userId == _userId }
    }

    suspend fun addTask(task: Task) = withContext(Dispatchers.IO) {
        db.taskDao().insert(task.copy(userId = _userId))
        // Optionally sync with server
    }

    suspend fun syncTasks() = withContext(Dispatchers.IO) {
        val remote = api.getTasks("Bearer $token").body()?.filter { it.userId == _userId } ?: emptyList()
        remote.forEach { db.taskDao().insert(it) }
        val local = db.taskDao().getAll().filter { it.userId == _userId }
        api.syncTasks("Bearer $token", local)
    }

    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        db.taskDao().update(task.copy(userId = _userId))
        // Optionally sync with server
    }

    suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        db.taskDao().delete(task)
        // Optionally sync with server
    }

    suspend fun logTaskCompletion(task: Task) = withContext(Dispatchers.IO) {
        db.historyEntryDao().insert(
            HistoryEntry(
                id = 0,
                userId = _userId,
                feature = "task",
                featureId = task.id,
                completedOn = Date()
            )
        )
    }
}
