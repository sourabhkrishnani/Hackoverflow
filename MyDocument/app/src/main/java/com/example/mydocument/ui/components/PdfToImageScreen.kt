package com.example.mydocument.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
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
fun PdfToImageScreen(navController: NavController) {
    var selectedPdf by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var outputUri by remember { mutableStateOf<Uri?>(null) }

    val pdfPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedPdf = uri
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.pdf_to_image)) },
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
                onClick = { pdfPicker.launch("application/pdf") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedPdf == null) "Select PDF" else "Change PDF")
            }

            if (selectedPdf != null) {
                Button(
                    onClick = {
                        val uri = selectedPdf
                        if (uri != null) {
                            scope.launch {
                                try {
                                    isLoading = true
                                    outputUri = convertPdfToImages(context, uri)
                                    showSuccessDialog = true
                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Error converting PDF to images"
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
                    Text(if (isLoading) "Converting..." else "Convert to Images")
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
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("PDF converted to images successfully") },
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

private suspend fun convertPdfToImages(context: Context, pdfUri: Uri): Uri {
    val outputDir = File(context.getExternalFilesDir(null), "PDFImages")
    outputDir.mkdirs()

    val outputFile = File(outputDir, "page_1.png")
    
    return withContext(Dispatchers.IO) {
        val input = context.contentResolver.openFileDescriptor(pdfUri, "r")
        input?.use { parcelFileDescriptor ->
            val renderer = PdfRenderer(parcelFileDescriptor)
            renderer.use { pdfRenderer ->
                val page = pdfRenderer.openPage(0)
                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()

                FileOutputStream(outputFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
            }
        }
        Uri.fromFile(outputFile)
    }
}
