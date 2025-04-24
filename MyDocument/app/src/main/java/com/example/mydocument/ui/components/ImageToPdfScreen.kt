package com.example.mydocument.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mydocument.R
import com.example.mydocument.utils.PdfConverter
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageToPdfScreen(navController: NavController) {
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var isProcessing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var outputUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            selectedImages = uris
        }
    }

    fun createOutputFile(context: Context): File {
        val timestamp = System.currentTimeMillis()
        val outputDir = File(context.getExternalFilesDir(null), "converted_pdfs")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        return File(outputDir, "converted_$timestamp.pdf")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.convert_to_pdf)) },
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
            // Select images button
            Button(
                onClick = { imageLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedImages.isEmpty()) "Select Images" else "Change Images")
            }

            // Show selected images
            if (selectedImages.isNotEmpty()) {
                Text(
                    text = "${selectedImages.size} images selected",
                    style = MaterialTheme.typography.bodyMedium
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedImages) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }

                // Convert button
                Button(
                    onClick = {
                        scope.launch {
                            isProcessing = true
                            errorMessage = null
                            try {
                                PdfConverter.convertImagesToPdf(
                                    context,
                                    selectedImages
                                ) { uri ->
                                    outputUri = uri
                                    showSuccessDialog = true
                                }
                            } catch (e: Exception) {
                                errorMessage = e.message ?: "Failed to convert images to PDF"
                            } finally {
                                isProcessing = false
                            }
                        }
                    },
                    enabled = selectedImages.isNotEmpty() && !isProcessing,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isProcessing) "Converting..." else "Convert to PDF")
                }
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
                text = { Text("Images have been converted to PDF successfully!") },
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
