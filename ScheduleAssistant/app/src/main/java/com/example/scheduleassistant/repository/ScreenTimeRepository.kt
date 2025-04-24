package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.ScreenTimeEntry
import com.example.scheduleassistant.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScreenTimeRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val _userId: String,
    private val token: String
) {
    val userId: String
        get() = _userId

    suspend fun getScreenTimeEntries(): List<ScreenTimeEntry> = withContext(Dispatchers.IO) {
        db.screenTimeEntryDao().getAll().filter { it.userId == _userId }
    }
    suspend fun addScreenTimeEntry(entry: ScreenTimeEntry) = withContext(Dispatchers.IO) {
        db.screenTimeEntryDao().insert(entry.copy(userId = _userId))
    }
    suspend fun syncScreenTimeEntries() = withContext(Dispatchers.IO) {
        val remote = api.getScreenTime("Bearer $token").body()?.filter { it.userId == _userId } ?: emptyList()
        remote.forEach { db.screenTimeEntryDao().insert(it) }
        val local = db.screenTimeEntryDao().getAll().filter { it.userId == _userId }
        api.syncScreenTime("Bearer $token", local)
    }
}
