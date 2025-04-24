package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.Category
import com.example.scheduleassistant.network.ApiService
import com.example.scheduleassistant.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val userId: String,
    private val token: String
) {
    suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
        db.categoryDao().getAll().filter { it.userId == userId }
    }
    suspend fun addCategory(category: Category) = withContext(Dispatchers.IO) {
        db.categoryDao().insert(category.copy(userId = userId))
    }
    suspend fun syncCategories() = withContext(Dispatchers.IO) {
        val remote = api.getCategories("Bearer $token").body()?.filter { it.userId == userId } ?: emptyList()
        remote.forEach { db.categoryDao().insert(it) }
        val local = db.categoryDao().getAll().filter { it.userId == userId }
        api.syncCategories("Bearer $token", local)
    }
}
