package com.example.mydocument.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mydocument.utils.FileDownloader
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PreviewScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    navController: NavController,
    type: String,
    uriString: String
) {
    val context = LocalContext.current
    var showRenameDialog by remember { mutableStateOf(false) }
    var fileName by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var isDownloading by remember { mutableStateOf(false) }
    
    // Parse the URI string
    val uri = try {
        Uri.parse(uriString)
    } catch (e: Exception) {
        Log.e(TAG, "Error parsing URI: $uriString", e)
        null
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Preview") },
                navigationIcon = {
                    // TODO: Replace with back arrow icon when material icons are properly set up
                    TextButton(
                        onClick = { 
                            if (!isDownloading) {
                                navController.navigateUp() 
                            }
                        },
                        enabled = !isDownloading
                    ) {
                        Text("Back")
                    }
                },
                actions = {
                    // TODO: Replace with save icon when material icons are properly set up
                    TextButton(
                        onClick = { showRenameDialog = true },
                        enabled = !isDownloading
                    ) {
                        Text("Save")
                    }
                    // TODO: Replace with download icon when material icons are properly set up
                    TextButton(
                        onClick = {
                            if (isDownloading || uri == null) return@TextButton
                            
                            isDownloading = true
                            try {
                                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                                    .format(Date())
                                
                                Log.d(TAG, "Starting download with URI: $uri")

                                val downloadFileName = when (type) {
                                    "pdf" -> "document_$timestamp.pdf"
                                    else -> "image_$timestamp.jpg"
                                }

                                val success = FileDownloader.downloadFile(
                                    context,
                                    uri,
                                    downloadFileName
                                )
                                if (success) {
                                    showSuccessDialog = true
                                } else {
                                    showErrorDialog = true
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error during download", e)
                                showErrorDialog = true
                            } finally {
                                isDownloading = false
                            }
                        },
                        enabled = !isDownloading && uri != null
                    ) {
                        Text(if (isDownloading) "Downloading..." else "Download")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (type) {
                "pdf" -> {
                    // PDF Preview using PDF viewer
                    // You might want to use a PDF viewer library here
                    Text(
                        text = "PDF Preview",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                "image" -> {
                    if (uri != null) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    } else {
                        Text(
                            text = "Invalid image URI",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                else -> {
                    Text(
                        text = "Unsupported file type",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (isDownloading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("Save File") },
            text = {
                OutlinedTextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = { Text("File Name") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        if (isDownloading || uri == null) return@TextButton

                        isDownloading = true
                        try {

                            Log.d(TAG, "Starting download with URI: $uri")
                            val downloadFileName = when (type) {
                                "pdf" -> "$fileName.pdf"
                                else -> "$fileName.jpg"
                            }

                            val success = FileDownloader.downloadFile(
                                context,
                                uri,
                                downloadFileName
                            )
                            if (success) {
                                showSuccessDialog = true
                            } else {
                                showErrorDialog = true
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error during download", e)
                            showErrorDialog = true
                        } finally {
                            isDownloading = false
                        }
                        showRenameDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("File has been downloaded successfully to Downloads folder") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Error Dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("Failed to download file. Please try again.") },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
