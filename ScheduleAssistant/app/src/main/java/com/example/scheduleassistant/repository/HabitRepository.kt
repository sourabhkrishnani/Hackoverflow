package com.example.scheduleassistant.repository

import android.content.Context
import com.example.scheduleassistant.BuildConfig
import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.Habit
import com.example.scheduleassistant.data.models.HistoryEntry
import com.example.scheduleassistant.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.Date

class HabitRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val userId: String,
    private val token: String
) {
    suspend fun getHabits(): List<Habit> = withContext(Dispatchers.IO) {
        db.habitDao().getAll().filter { it.userId == userId }
    }

    suspend fun addHabit(habit: Habit) = withContext(Dispatchers.IO) {
        db.habitDao().insert(habit.copy(userId = userId))
        // Optionally sync with server
    }

    suspend fun updateHabit(habit: Habit) = withContext(Dispatchers.IO) {
        db.habitDao().update(habit.copy(userId = userId))
        // Optionally sync with server
    }

    suspend fun logHabitCompletion(habit: Habit) = withContext(Dispatchers.IO) {
        db.historyEntryDao().insert(
            HistoryEntry(
                id = 0,
                userId = userId,
                feature = "habit",
                featureId = habit.id,
                completedOn = Date()
            )
        )
    }

    suspend fun deleteHabit(habit: Habit) = withContext(Dispatchers.IO) {
        db.habitDao().delete(habit)
        // Optionally sync with server
    }

    suspend fun syncHabits() = withContext(Dispatchers.IO) {
        // Download from server
        val remote = api.getHabits("Bearer $token").body()?.filter { it.userId == userId } ?: emptyList()
        remote.forEach { db.habitDao().insert(it) }
        // Upload local to server
        val local = db.habitDao().getAll().filter { it.userId == userId }
        api.syncHabits("Bearer $token", local)
    }

    fun getUserId(): String = userId
}
