package com.example.mydocument.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileDownloader {
    private const val TAG = "FileDownloader"

    fun downloadFile(context: Context, sourceUri: Uri, fileName: String): Boolean {
        Log.d(TAG, "Starting download for file: $fileName, URI: $sourceUri")
        
        if (sourceUri == Uri.EMPTY) {
            Log.e(TAG, "Source URI is empty")
            return false
        }

        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.d(TAG, "Using MediaStore method (Android 10+)")
                downloadUsingMediaStore(context, sourceUri, fileName)
            } else {
                Log.d(TAG, "Using legacy method (Pre-Android 10)")
                downloadUsingLegacyMethod(context, sourceUri, fileName)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading file", e)
            false
        }
    }

    @androidx.annotation.RequiresApi(Build.VERSION_CODES.Q)
    private fun downloadUsingMediaStore(context: Context, sourceUri: Uri, fileName: String): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, getMimeType(fileName))
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val destinationUri = resolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (destinationUri == null) {
            Log.e(TAG, "Failed to create destination URI")
            return false
        }

        return try {
            val inputStream = resolver.openInputStream(sourceUri)
            if (inputStream == null) {
                Log.e(TAG, "Failed to open input stream from source URI")
                return false
            }

            inputStream.use { input ->
                resolver.openOutputStream(destinationUri)?.use { output ->
                    input.copyTo(output)
                    Log.d(TAG, "File downloaded successfully using MediaStore")
                    true
                } ?: run {
                    Log.e(TAG, "Failed to open output stream")
                    false
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error during file copy", e)
            resolver.delete(destinationUri, null, null)
            false
        }
    }

    private fun downloadUsingLegacyMethod(context: Context, sourceUri: Uri, fileName: String): Boolean {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists() && !downloadsDir.mkdirs()) {
            Log.e(TAG, "Failed to create Downloads directory")
            return false
        }

        val destinationFile = File(downloadsDir, fileName)
        return try {
            val inputStream = context.contentResolver.openInputStream(sourceUri)
            if (inputStream == null) {
                Log.e(TAG, "Failed to open input stream from source URI")
                return false
            }

            inputStream.use { input ->
                FileOutputStream(destinationFile).use { output ->
                    input.copyTo(output)
                    Log.d(TAG, "File downloaded successfully using legacy method")
                }
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Error during file copy", e)
            if (destinationFile.exists()) {
                destinationFile.delete()
            }
            false
        }
    }

    private fun getMimeType(fileName: String): String {
        return when {
            fileName.endsWith(".pdf", ignoreCase = true) -> "application/pdf"
            fileName.endsWith(".jpg", ignoreCase = true) -> "image/jpeg"
            fileName.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
            fileName.endsWith(".png", ignoreCase = true) -> "image/png"
            else -> "*/*"
        }
    }
}
