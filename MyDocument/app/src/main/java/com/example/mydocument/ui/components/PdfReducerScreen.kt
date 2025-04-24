package com.example.mydocument.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mydocument.utils.PdfConverter
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfReducerScreen(navController: NavController) {
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var targetSizeKB by remember { mutableStateOf("500") }
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var outputUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedPdfUri = uri
    }

    fun createOutputFile(context: Context): File {
        val timestamp = System.currentTimeMillis()
        val outputDir = File(context.getExternalFilesDir(null), "compressed_pdfs")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        return File(outputDir, "compressed_$timestamp.pdf")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reduce PDF Size") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Select PDF button
            Button(
                onClick = { pdfLauncher.launch("application/pdf") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedPdfUri == null) "Select PDF" else "Change PDF")
            }

            // Show selected file name
            selectedPdfUri?.let {
                Text(
                    text = "Selected: ${it.lastPathSegment ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Target size input
            OutlinedTextField(
                value = targetSizeKB,
                onValueChange = { targetSizeKB = it },
                label = { Text("Target Size (KB)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                enabled = !isProcessing
            )

            // Reduce PDF button
            Button(
                onClick = {
                    scope.launch {
                        isProcessing = true
                        errorMessage = null
                        try {
                            PdfConverter.resizePdf(
                                context,
                                selectedPdfUri!!,
                                targetSizeKB.toIntOrNull() ?: 500
                            ) { uri ->
                                outputUri = uri
                                showSuccessDialog = true
                            }
                        } catch (e: Exception) {
                            errorMessage = e.message ?: "Failed to reduce PDF"
                        } finally {
                            isProcessing = false
                        }
                    }
                },
                enabled = selectedPdfUri != null && !isProcessing && targetSizeKB.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isProcessing) "Processing..." else "Reduce PDF")
            }

            // Error message
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (isProcessing) {
                CircularProgressIndicator()
            }
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Success") },
                text = { Text("PDF has been reduced successfully!") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            // Navigate to preview screen with encoded URI
                            outputUri?.let { uri ->
                                val encodedUri = Uri.encode(uri.toString())
                                navController.navigate("preview/pdf/$encodedUri")
                            }
                        }
                    ) {
                        Text("View PDF")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSuccessDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
