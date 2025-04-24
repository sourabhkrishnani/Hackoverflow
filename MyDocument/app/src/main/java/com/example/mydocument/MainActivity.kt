package com.example.mydocument

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mydocument.navigation.Screen
import com.example.mydocument.ui.components.*
import com.example.mydocument.ui.theme.MyDocumentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
            setContent {
                MyDocumentTheme(dynamicColor = true) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            
            composable(Screen.ImageToPdf.route) {
                ImageToPdfScreen(navController)
            }
            
            composable(Screen.ResizeImage.route) {
                ResizeImageScreen(navController)
            }
            
            composable(Screen.PdfToImage.route) {
                PdfToImageScreen(navController)
            }
            
            composable(Screen.PdfReducer.route) {
                PdfReducerScreen(navController)
            }

            // Preview screen routes
            composable(
                route = "preview/{type}/{uri}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("uri") { 
                        type = NavType.StringType
                        nullable = true 
                    }
                )
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: return@composable
                val uriString = backStackEntry.arguments?.getString("uri") ?: return@composable
                PreviewScreen(
                    navController = navController,
                    type = type,
                    uriString = uriString
                )
            }
        }
    }
}


//
//@Preview
//@Composable
//fun PreviewAll(){
//    val navController = rememberNavController()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Document Converter",
//            style = MaterialTheme.typography.headlineMedium,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(bottom = 32.dp),
//            fontWeight = FontWeight.Bold
//        )
//
//        FeatureButton(
//            text = "Convert Images to PDF",
//            onClick = { navController.navigate(Screen.ImageToPdf.route) }
//        )
//
//        FeatureButton(
//            text = "Resize Image",
//            onClick = { navController.navigate(Screen.ResizeImage.route) }
//        )
//
//        FeatureButton(
//            text = "Convert PDF to Images",
//            onClick = { navController.navigate(Screen.PdfToImage.route) }
//        )
//
//        FeatureButton(
//            text = "Reduce PDF Size",
//            onClick = { navController.navigate(Screen.PdfReducer.route) }
//        )
//    }
//
//
//}
//@Composable
//private fun FeatureButton(
//    text: String,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//    }
//}
