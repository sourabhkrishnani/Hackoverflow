package com.example.scheduleassistant

import android.content.Context
import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.network.ApiService
import com.example.scheduleassistant.repository.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.room.Room

class AppContainer(context: Context) {
    // Database
    val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "schedule_assistant_db"
    ).build()
    // Network
    val api: ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
    // Auth
    val authRepository = AuthRepository(context)
    // Repositories (userId/token will be set after login)
    var habitRepository: HabitRepository? = null
    var taskRepository: TaskRepository? = null
    var expenseRepository: ExpenseRepository? = null
    var waterIntakeRepository: WaterIntakeRepository? = null
    var nutritionRepository: NutritionRepository? = null
    var screenTimeRepository: ScreenTimeRepository? = null
    var scheduleRepository: ScheduleRepository? = null
    var categoryRepository: CategoryRepository? = null
    var historyRepository: HistoryRepository? = null

    fun setupUserRepositories(userId: String, token: String) {
        habitRepository = HabitRepository(db, api, userId, token)
        taskRepository = TaskRepository(db, api, userId, token)
        expenseRepository = ExpenseRepository(db, api, userId, token)
        waterIntakeRepository = WaterIntakeRepository(db, api, userId, token)
        nutritionRepository = NutritionRepository(db, api, userId, token)
        screenTimeRepository = ScreenTimeRepository(db, api, userId, token)
        scheduleRepository = ScheduleRepository(db, api, userId, token)
        categoryRepository = CategoryRepository(db, api, userId, token)
        historyRepository = HistoryRepository(db, api, userId, token)
    }
}
