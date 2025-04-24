package com.example.scheduleassistant.repository

import com.example.scheduleassistant.data.AppDatabase
import com.example.scheduleassistant.data.models.Expense
import com.example.scheduleassistant.data.models.Earning
import com.example.scheduleassistant.network.ApiService
import com.example.scheduleassistant.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseRepository(
    private val db: AppDatabase,
    private val api: ApiService,
    private val userId: String,
    private val token: String
) {
    suspend fun getExpenses(): List<Expense> = withContext(Dispatchers.IO) {
        db.expenseDao().getAll().filter { it.userId == userId }
    }
    suspend fun getEarnings(): List<Earning> = withContext(Dispatchers.IO) {
        db.earningDao().getAll().filter { it.userId == userId }
    }
    suspend fun addExpense(expense: Expense) = withContext(Dispatchers.IO) {
        db.expenseDao().insert(expense.copy(userId = userId))
    }
    suspend fun addEarning(earning: Earning) = withContext(Dispatchers.IO) {
        db.earningDao().insert(earning.copy(userId = userId))
    }
    suspend fun syncExpensesAndEarnings() = withContext(Dispatchers.IO) {
        val remoteExpenses = api.getExpenses("Bearer $token").body()?.filter { it.userId == userId } ?: emptyList()
        remoteExpenses.forEach { db.expenseDao().insert(it) }
        val localExpenses = db.expenseDao().getAll().filter { it.userId == userId }
        api.syncExpenses("Bearer $token", localExpenses)
        val remoteEarnings = api.getEarnings("Bearer $token").body()?.filter { it.userId == userId } ?: emptyList()
        remoteEarnings.forEach { db.earningDao().insert(it) }
        val localEarnings = db.earningDao().getAll().filter { it.userId == userId }
        api.syncEarnings("Bearer $token", localEarnings)
    }

    // Public getter for userId
    fun getUserId(): String = userId
}
