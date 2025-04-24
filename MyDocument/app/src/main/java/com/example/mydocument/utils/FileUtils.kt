package com.example.mydocument.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {
    fun createImageFile(context: Context): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    fun createPdfFile(context: Context, prefix: String = "PDF"): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "${prefix}_${timeStamp}.pdf"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File(storageDir, fileName)
    }

    fun getFileSize(context: Context, uri: Uri): Long {
        return context.contentResolver.openFileDescriptor(uri, "r")?.use {
            it.statSize
        } ?: 0L
    }

    fun getFileName(context: Context, uri: Uri): String {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                return cursor.getString(nameIndex)
            }
        }
        return uri.lastPathSegment ?: "unknown"
    }
}
