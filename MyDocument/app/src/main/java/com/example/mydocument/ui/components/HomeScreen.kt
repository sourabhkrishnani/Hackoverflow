package com.example.mydocument.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mydocument.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Document Converter",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        FeatureButton(
            text = "Convert Images to PDF",
            onClick = { navController.navigate(Screen.ImageToPdf.route) }
        )
        
        FeatureButton(
            text = "Resize Image",
            onClick = { navController.navigate(Screen.ResizeImage.route) }
        )
        
        FeatureButton(
            text = "Convert PDF to Images",
            onClick = { navController.navigate(Screen.PdfToImage.route) }
        )
        
        FeatureButton(
            text = "Reduce PDF Size",
            onClick = { navController.navigate(Screen.PdfReducer.route) }
        )
    }
}

@Composable
private fun FeatureButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
