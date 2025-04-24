package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.HistoryEntry
import com.example.scheduleassistant.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.scheduleassistant.BuildConfig

class HistoryRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val userId: String,
    private val token: String
) {
    suspend fun getHistoryEntries(): List<HistoryEntry> = withContext(Dispatchers.IO) {
        db.historyEntryDao().getAll().filter { it.userId == userId }
    }
    suspend fun addHistoryEntry(entry: HistoryEntry) = withContext(Dispatchers.IO) {
        db.historyEntryDao().insert(entry.copy(userId = userId))
    }
    suspend fun syncHistoryEntries() = withContext(Dispatchers.IO) {
        val remote = api.getHistory("Bearer $token").body()?.filter { it.userId == userId } ?: emptyList()
        remote.forEach { db.historyEntryDao().insert(it) }
        val local = db.historyEntryDao().getAll().filter { it.userId == userId }
        api.syncHistory("Bearer $token", local)
    }
}
