package com.example.scheduleassistant.repository

import android.content.Context
import com.example.scheduleassistant.BuildConfig
import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.ScheduleEntry
import com.example.scheduleassistant.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val _userId: String,
    private val token: String
) {
    val userId: String
        get() = _userId

    suspend fun getScheduleEntries(): List<ScheduleEntry> = withContext(Dispatchers.IO) {
        db.scheduleEntryDao().getAll().filter { it.userId == _userId }
    }
    suspend fun addScheduleEntry(entry: ScheduleEntry) = withContext(Dispatchers.IO) {
        db.scheduleEntryDao().insert(entry.copy(userId = _userId))
    }
    suspend fun syncScheduleEntries() = withContext(Dispatchers.IO) {
        val remote = api.getSchedule("Bearer $token").body()?.filter { it.userId == _userId } ?: emptyList()
        remote.forEach { db.scheduleEntryDao().insert(it) }
        val local = db.scheduleEntryDao().getAll().filter { it.userId == _userId }
        api.syncSchedule("Bearer $token", local)
    }

    suspend fun updateScheduleEntry(entry: ScheduleEntry) = withContext(Dispatchers.IO) {
        db.scheduleEntryDao().update(entry.copy(userId = _userId))
        // Optionally sync with server
    }

    suspend fun deleteScheduleEntry(entry: ScheduleEntry) = withContext(Dispatchers.IO) {
        db.scheduleEntryDao().delete(entry)
        // Optionally sync with server
    }

    suspend fun logScheduleCompletion(entry: ScheduleEntry) = withContext(Dispatchers.IO) {
        db.historyEntryDao().insert(
            com.example.scheduleassistant.data.models.HistoryEntry(
                id = 0,
                userId = _userId,
                feature = "schedule",
                featureId = entry.id,
                completedOn = java.util.Date()
            )
        )
    }
}
