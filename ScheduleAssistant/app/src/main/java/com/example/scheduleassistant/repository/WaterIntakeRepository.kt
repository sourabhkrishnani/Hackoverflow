package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.WaterIntake
import com.example.scheduleassistant.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WaterIntakeRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val _userId: String,
    private val token: String
) {
    val userId: String
        get() = _userId
    suspend fun getWaterIntake(): List<WaterIntake> = withContext(Dispatchers.IO) {
        db.waterIntakeDao().getAll().filter { it.userId == _userId }
    }
    suspend fun addWaterIntake(water: WaterIntake) = withContext(Dispatchers.IO) {
        db.waterIntakeDao().insert(water.copy(userId = _userId))
    }
    suspend fun syncWaterIntake() = withContext(Dispatchers.IO) {
        val remote = api.getWaterIntake("Bearer $token").body()?.filter { it.userId == _userId } ?: emptyList()
        remote.forEach { db.waterIntakeDao().insert(it) }
        val local = db.waterIntakeDao().getAll().filter { it.userId == _userId }
        api.syncWaterIntake("Bearer $token", local)
    }
}
