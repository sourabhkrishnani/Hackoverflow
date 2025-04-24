package com.example.mydocument.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
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
import com.example.mydocument.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResizeImageScreen(navController: NavController) {
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var targetSize by remember { mutableStateOf("500") }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var outputUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImage = uri
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.resize_image)) },
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
            Button(
                onClick = { imagePicker.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedImage == null) "Select Image" else "Change Image")
            }

            OutlinedTextField(
                value = targetSize,
                onValueChange = { targetSize = it },
                label = { Text(stringResource(R.string.target_size_kb)) },
                modifier = Modifier.fillMaxWidth()
            )

            if (selectedImage != null) {
                Button(
                    onClick = {
                        val uri = selectedImage
                        if (uri != null) {
                            scope.launch {
                                try {
                                    isLoading = true
                                    val targetSizeKB = targetSize.toIntOrNull() ?: 500
                                    outputUri = resizeImage(context, uri, targetSizeKB)
                                    showSuccessDialog = true
                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Error resizing image"
                                    showError = true
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isLoading) stringResource(R.string.resizing) else "Resize Image")
                }
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }

    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text(stringResource(R.string.error)) },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showError = false }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("Image resized successfully") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        // Navigate to preview screen with encoded URI
                        outputUri?.let { uri ->
                            val encodedUri = Uri.encode(uri.toString())
                            navController.navigate("preview/image/$encodedUri")
                        }
                    }
                ) {
                    Text("View Image")
                }
            }
        )
    }
}

private suspend fun resizeImage(context: Context, imageUri: Uri, targetSizeKB: Int): Uri {
    val outputDir = File(context.getExternalFilesDir(null), "ResizedImages")
    outputDir.mkdirs()

    val outputFile = File(outputDir, "resized_${System.currentTimeMillis()}.jpg")
    
    return withContext(Dispatchers.IO) {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream?.close()

        var quality = 100
        var fileSize: Long
        
        do {
            context.contentResolver.openInputStream(imageUri)?.use { input ->
                val bitmap = BitmapFactory.decodeStream(input)
                FileOutputStream(outputFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                }
            }
            
            fileSize = outputFile.length() / 1024 // Convert to KB
            quality -= 5
        } while (fileSize > targetSizeKB && quality > 5)

        Uri.fromFile(outputFile)
    }
}
