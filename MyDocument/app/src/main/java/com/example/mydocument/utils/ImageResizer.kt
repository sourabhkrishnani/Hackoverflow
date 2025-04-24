package com.example.mydocument.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageResizer {
    companion object {
        suspend fun resizeImage(context: Context, inputUri: Uri, targetSizeKB: Int, outputFile: File) {
            withContext(Dispatchers.IO) {
                // Load the original bitmap
                val inputStream = context.contentResolver.openInputStream(inputUri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // Calculate target size in bytes
                val targetBytes = targetSizeKB * 1024

                // Start with 100% quality
                var quality = 100
                var compressedSize: Long

                // Create a temporary file to check compressed size
                val tempFile = File(context.cacheDir, "temp.jpg")
                
                do {
                    FileOutputStream(tempFile).use { out ->
                        originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                    }
                    compressedSize = tempFile.length()
                    
                    // Reduce quality by 5% each time
                    quality -= 5
                } while (compressedSize > targetBytes && quality > 5)

                // Copy the final result to the output file
                tempFile.copyTo(outputFile, overwrite = true)
                tempFile.delete()
                
                originalBitmap.recycle()
            }
        }
    }
}
