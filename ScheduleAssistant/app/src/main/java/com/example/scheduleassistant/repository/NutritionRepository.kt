package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.NutritionEntry
import com.example.scheduleassistant.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.scheduleassistant.BuildConfig

class NutritionRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val _userId: String,
    private val token: String
) {
    val userId: String
        get() = _userId

    suspend fun getNutritionEntries(): List<NutritionEntry> = withContext(Dispatchers.IO) {
        db.nutritionEntryDao().getAll().filter { it.userId == _userId }
    }
    suspend fun addNutritionEntry(entry: NutritionEntry) = withContext(Dispatchers.IO) {
        db.nutritionEntryDao().insert(entry.copy(userId = _userId))
    }
    suspend fun syncNutritionEntries() = withContext(Dispatchers.IO) {
        val remote = api.getNutrition("Bearer $token").body()?.filter { it.userId == _userId } ?: emptyList()
        remote.forEach { db.nutritionEntryDao().insert(it) }
        val local = db.nutritionEntryDao().getAll().filter { it.userId == _userId }
        api.syncNutrition("Bearer $token", local)
    }
}
