package com.example.drbharti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drbharti.ui.theme.DrBhartiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrBhartiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrBhartiApp()
                }
            }
        }
    }
}

@Composable
fun DrBhartiApp() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "get_started") {
        composable("get_started") {
            GetStartedScreen(
                onGetStarted = { 
                    try {
                        navController.navigate("welcome")
                    } catch (e: Exception) {
                        // Log the error or handle it
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable("welcome") {
            WelcomeScreen(
                onNavigateToLogin = { 
                    try {
                        navController.navigate("login")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                onNavigateToRegister = { 
                    try {
                        navController.navigate("signup")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable("splash") {
            SplashScreen(
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        
        composable("login") {
            LoginScreen(
                onLogin = { 
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = false }
                    }
                },
                onSignUp = { navController.navigate("signup") },
                onForgotPassword = { /* Handle forgot password */ }
            )
        }
        
        // New SignUp screen that matches the design
        composable("signup") {
            SignUpScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmit = { navController.navigate("user_details") }
            )
        }
        
        // Original registration screen (can be kept for backward compatibility)
        composable("register") {
            RegistrationScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmit = { navController.navigate("user_details") },
                onSignInWithGoogle = { 
                    // Handle Google sign in and then navigate to home
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = false }
                    }
                }
            )
        }
        
        composable("user_details") {
            UserDetailsScreen(
                onNavigateBack = { navController.popBackStack() },
                onSubmit = { 
                    try {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onNavigate = { route -> 
                    try {
                        navController.navigate(route)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                onDoctorSelected = { doctorId -> 
                    navController.navigate("doctor/$doctorId") 
                }
            )
        }
        
        // New Appointment screen that matches the design
        composable("appointment") {
            AppointmentScreen(
                onNavigate = { route -> 
                    try {
                        navController.navigate(route)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable("articles") {
            ArticlesScreen(
                onNavigate = { route -> 
                    try {
                        navController.navigate(route)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                onArticleSelected = { articleId ->
                    // Navigate to article detail when implemented
                }
            )
        }
        
        composable("communities") {
            CommunitiesScreen(
                onNavigate = { route -> 
                    try {
                        navController.navigate(route)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable("progress") {
            ProgressScreen(
                onNavigate = { route -> 
                    try {
                        navController.navigate(route)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        // Add route for NutrientsIntakeScreen
        composable("nutrients_intake") {
            NutrientsIntakeScreen(
                onNavigateBack = { 
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                onSubmit = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            )
        }
        
        composable(
            "doctor/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            DoctorDetailScreen(
                doctorId = doctorId,
                onNavigateBack = { navController.popBackStack() },
                onStartVideoCall = { /* Handle video call */ },
                onStartVoiceCall = { /* Handle voice call */ },
                onStartChat = { /* Handle chat */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DrBhartiTheme {
        DrBhartiApp()
    }
}