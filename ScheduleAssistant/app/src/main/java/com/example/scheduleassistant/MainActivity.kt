package com.example.scheduleassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.scheduleassistant.ui.navigation.AppNavGraph
import com.example.scheduleassistant.ui.theme.ScheduleAssistantTheme
import com.example.scheduleassistant.AppContainer
import com.example.scheduleassistant.ui.habit.HabitViewModel
import com.example.scheduleassistant.ui.task.TaskViewModel
import com.example.scheduleassistant.ui.expense.ExpenseViewModel
import com.example.scheduleassistant.ui.water.WaterViewModel
import com.example.scheduleassistant.ui.nutrition.NutritionViewModel
import com.example.scheduleassistant.ui.screentime.ScreenTimeViewModel
import com.example.scheduleassistant.ui.schedule.ScheduleViewModel
import com.example.scheduleassistant.ui.category.CategoryViewModel
import com.example.scheduleassistant.ui.history.HistoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appContainer = AppContainer(applicationContext)
        val authRepo = appContainer.authRepository
        var token = authRepo.getToken()
        var userId = authRepo.getUserId()
        if (token != null && userId != null) {
            appContainer.setupUserRepositories(userId, token)
        }
        setContent {
            ScheduleAssistantTheme {
                var isLoggedIn by remember { mutableStateOf(token != null && userId != null) }
                var currentUserId by remember { mutableStateOf(userId) }
                var currentToken by remember { mutableStateOf(token) }
                val habitViewModel = remember(currentUserId, currentToken) { appContainer.habitRepository?.let { HabitViewModel(it) } }
                val taskViewModel = remember(currentUserId, currentToken) { appContainer.taskRepository?.let { TaskViewModel(it) } }
                val expenseViewModel = remember(currentUserId, currentToken) { appContainer.expenseRepository?.let { ExpenseViewModel(it) } }
                val waterViewModel = remember(currentUserId, currentToken) { appContainer.waterIntakeRepository?.let { WaterViewModel(it) } }
                val nutritionViewModel = remember(currentUserId, currentToken) { appContainer.nutritionRepository?.let { NutritionViewModel(it) } }
                val screenTimeViewModel = remember(currentUserId, currentToken) { appContainer.screenTimeRepository?.let { ScreenTimeViewModel(it) } }
                val scheduleViewModel = remember(currentUserId, currentToken) { appContainer.scheduleRepository?.let { ScheduleViewModel(it) } }
                val categoryViewModel = remember(currentUserId, currentToken) { appContainer.categoryRepository?.let { CategoryViewModel(it) } }
                val historyViewModel = remember(currentUserId, currentToken) { appContainer.historyRepository?.let { HistoryViewModel(it) } }
                AppNavGraph(
                    isLoggedIn = isLoggedIn,
                    onLogin = { email, password ->
                        // TEMPORARY TEST LOGIN HANDLING - REMOVE IN FINAL VERSION
                        if (email == "testuser@example.com" && password == "testpassword") {
                            val newToken = "FAKE_TOKEN_FOR_TEST"
                            val newUserId = "test_user_id"
                            appContainer.setupUserRepositories(newUserId, newToken)
                            currentUserId = newUserId
                            currentToken = newToken
                            token = newToken
                            userId = newUserId
                            isLoggedIn = true
                        } else {
                            // END TEMPORARY BLOCK
                            CoroutineScope(Dispatchers.IO).launch {
                                val result = authRepo.login(email, password)
                                if (result != null) {
                                    val newToken = result.token
                                    val newUserId = result.userId
                                    appContainer.setupUserRepositories(newUserId, newToken)
                                    withContext(Dispatchers.Main) {
                                        currentUserId = newUserId
                                        currentToken = newToken
                                        token = newToken
                                        userId = newUserId
                                        isLoggedIn = true
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        // Optionally show error message to user
                                    }
                                }
                            }
                        }
                    },
                    onSignup = { email, password, name ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = authRepo.signup(email, password, name)
                            if (result != null) {
                                val newToken = result.token
                                val newUserId = result.userId
                                appContainer.setupUserRepositories(newUserId, newToken)
                                withContext(Dispatchers.Main) {
                                    currentUserId = newUserId
                                    currentToken = newToken
                                    token = newToken
                                    userId = newUserId
                                    isLoggedIn = true
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    // Optionally show error message to user
                                }
                            }
                        }
                    },
                    habitViewModel = habitViewModel,
                    taskViewModel = taskViewModel,
                    expenseViewModel = expenseViewModel,
                    waterViewModel = waterViewModel,
                    nutritionViewModel = nutritionViewModel,
                    screenTimeViewModel = screenTimeViewModel,
                    scheduleViewModel = scheduleViewModel,
                    categoryViewModel = categoryViewModel,
                    historyViewModel = historyViewModel
                )
            }
        }
    }
}
