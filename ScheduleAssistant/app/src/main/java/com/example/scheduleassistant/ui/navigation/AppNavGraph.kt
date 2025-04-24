package com.example.scheduleassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.scheduleassistant.ui.auth.LoginScreen
import com.example.scheduleassistant.ui.auth.SignupScreen
import com.example.scheduleassistant.ui.habit.HabitTrackerScreen
import com.example.scheduleassistant.ui.task.TaskTrackerScreen
import com.example.scheduleassistant.ui.expense.ExpenseEarningsScreen
import com.example.scheduleassistant.ui.water.WaterIntakeScreen
import com.example.scheduleassistant.ui.nutrition.NutritionTrackerScreen
import com.example.scheduleassistant.ui.screentime.ScreenTimeScreen
import com.example.scheduleassistant.ui.schedule.ScheduleScreen
import com.example.scheduleassistant.ui.category.CategoryManagementScreen
import com.example.scheduleassistant.ui.history.HistoryScreen
import com.example.scheduleassistant.ui.habit.HabitViewModel
import com.example.scheduleassistant.ui.task.TaskViewModel
import com.example.scheduleassistant.ui.expense.ExpenseViewModel
import com.example.scheduleassistant.ui.water.WaterViewModel
import com.example.scheduleassistant.ui.nutrition.NutritionViewModel
import com.example.scheduleassistant.ui.screentime.ScreenTimeViewModel
import com.example.scheduleassistant.ui.schedule.ScheduleViewModel
import com.example.scheduleassistant.ui.category.CategoryViewModel
import com.example.scheduleassistant.ui.history.HistoryViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Habit : Screen("habit")
    object Task : Screen("task")
    object Expense : Screen("expense")
    object Water : Screen("water")
    object Nutrition : Screen("nutrition")
    object ScreenTime : Screen("screentime")
    object Schedule : Screen("schedule")
    object Category : Screen("category")
    object History : Screen("history")
}

@Composable
fun AppNavGraph(
    isLoggedIn: Boolean,
    onLogin: (String, String) -> Unit,
    onSignup: (String, String, String) -> Unit,
    navController: NavHostController? = null,
    habitViewModel: HabitViewModel? = null,
    taskViewModel: TaskViewModel? = null,
    expenseViewModel: ExpenseViewModel? = null,
    waterViewModel: WaterViewModel? = null,
    nutritionViewModel: NutritionViewModel? = null,
    screenTimeViewModel: ScreenTimeViewModel? = null,
    scheduleViewModel: ScheduleViewModel? = null,
    categoryViewModel: CategoryViewModel? = null,
    historyViewModel: HistoryViewModel? = null
) {
    val startDestination = if (isLoggedIn) "habit" else "login"
    NavHost(
        navController = navController ?: androidx.navigation.compose.rememberNavController(),
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                onLogin = onLogin,
                onNavigateToSignup = { navController?.navigate("signup") }
            )
        }
        composable("signup") {
            SignupScreen(
                onSignup = onSignup,
                onNavigateToLogin = { navController?.navigate("login") }
            )
        }
        composable("habit") { HabitTrackerScreen(viewModel = habitViewModel!!) }
        composable("task") { TaskTrackerScreen(viewModel = taskViewModel!!) }
        composable("expense") { ExpenseEarningsScreen(viewModel = expenseViewModel!!) }
        composable("water") { WaterIntakeScreen(viewModel = waterViewModel!!) }
        composable("nutrition") { NutritionTrackerScreen(viewModel = nutritionViewModel!!) }
        composable("screentime") { ScreenTimeScreen(viewModel = screenTimeViewModel!!) }
        composable("schedule") { ScheduleScreen(viewModel = scheduleViewModel!!) }
        composable("category") { CategoryManagementScreen(viewModel = categoryViewModel!!) }
        composable("history") { HistoryScreen(viewModel = historyViewModel!!) }
    }
}
