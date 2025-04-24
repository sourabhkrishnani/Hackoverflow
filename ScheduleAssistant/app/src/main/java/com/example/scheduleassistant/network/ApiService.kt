package com.example.scheduleassistant.network

import com.example.scheduleassistant.data.models.*
import retrofit2.Response
import retrofit2.http.*

// --- AUTH ---
data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val userId: String, val token: String)
data class SignupRequest(val email: String, val password: String, val name: String)

interface ApiService {
    // Auth
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<AuthResponse>

    // --- SYNC ---
    @GET("sync/habits")
    suspend fun getHabits(@Header("Authorization") token: String): Response<List<Habit>>
    @POST("sync/habits")
    suspend fun syncHabits(@Header("Authorization") token: String, @Body habits: List<Habit>): Response<Unit>

    @GET("sync/tasks")
    suspend fun getTasks(@Header("Authorization") token: String): Response<List<Task>>
    @POST("sync/tasks")
    suspend fun syncTasks(@Header("Authorization") token: String, @Body tasks: List<Task>): Response<Unit>

    @GET("sync/expenses")
    suspend fun getExpenses(@Header("Authorization") token: String): Response<List<Expense>>
    @POST("sync/expenses")
    suspend fun syncExpenses(@Header("Authorization") token: String, @Body expenses: List<Expense>): Response<Unit>

    @GET("sync/earnings")
    suspend fun getEarnings(@Header("Authorization") token: String): Response<List<Earning>>
    @POST("sync/earnings")
    suspend fun syncEarnings(@Header("Authorization") token: String, @Body earnings: List<Earning>): Response<Unit>

    @GET("sync/water")
    suspend fun getWaterIntake(@Header("Authorization") token: String): Response<List<WaterIntake>>
    @POST("sync/water")
    suspend fun syncWaterIntake(@Header("Authorization") token: String, @Body water: List<WaterIntake>): Response<Unit>

    @GET("sync/nutrition")
    suspend fun getNutrition(@Header("Authorization") token: String): Response<List<NutritionEntry>>
    @POST("sync/nutrition")
    suspend fun syncNutrition(@Header("Authorization") token: String, @Body nutrition: List<NutritionEntry>): Response<Unit>

    @GET("sync/screentime")
    suspend fun getScreenTime(@Header("Authorization") token: String): Response<List<ScreenTimeEntry>>
    @POST("sync/screentime")
    suspend fun syncScreenTime(@Header("Authorization") token: String, @Body screentime: List<ScreenTimeEntry>): Response<Unit>

    @GET("sync/schedule")
    suspend fun getSchedule(@Header("Authorization") token: String): Response<List<ScheduleEntry>>
    @POST("sync/schedule")
    suspend fun syncSchedule(@Header("Authorization") token: String, @Body schedule: List<ScheduleEntry>): Response<Unit>

    @GET("sync/categories")
    suspend fun getCategories(@Header("Authorization") token: String): Response<List<Category>>
    @POST("sync/categories")
    suspend fun syncCategories(@Header("Authorization") token: String, @Body categories: List<Category>): Response<Unit>

    @GET("sync/history")
    suspend fun getHistory(@Header("Authorization") token: String): Response<List<HistoryEntry>>
    @POST("sync/history")
    suspend fun syncHistory(@Header("Authorization") token: String, @Body history: List<HistoryEntry>): Response<Unit>
}
