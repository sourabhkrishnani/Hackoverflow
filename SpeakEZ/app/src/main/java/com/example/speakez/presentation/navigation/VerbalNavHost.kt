package com.example.speakez.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.speakez.presentation.analysis.AnalysisScreen
import com.example.speakez.presentation.history.HistoryScreen
import com.example.speakez.presentation.home.HomeScreen
import com.example.speakez.presentation.onboarding.OnboardingScreen
import com.example.speakez.presentation.practice.PracticeScreen

@Composable
fun VerbalNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding") {

        composable("onboarding") {
            OnboardingScreen(
                onContinue = { goal ->
                    navController.navigate("home/$goal")
                }
            )
        }

        composable(
            "home/{userGoal}",
            arguments = listOf(navArgument("userGoal") { type = NavType.StringType })
        ) { backStackEntry ->
            val userGoal = backStackEntry.arguments?.getString("userGoal") ?: "General"
            HomeScreen(
                userGoal = userGoal,
                onStartSession = { navController.navigate("practice/$userGoal") },
                onViewHistory = { navController.navigate("history") }
            )
        }

        composable(
            "practice/{userGoal}",
            arguments = listOf(navArgument("userGoal") { type = NavType.StringType })
        ) {
            PracticeScreen()
        }

        composable(
            "analysis/{sessionId}",
            arguments = listOf(navArgument("sessionId") { type = NavType.IntType })
        ) {
            AnalysisScreen()
        }

        composable("history") {
            HistoryScreen(
                onSessionClick = { sessionId ->
                    navController.navigate("analysis/$sessionId")
                }
            )
        }
    }
}
