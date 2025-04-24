package com.example.mydocument.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import com.tom_roush.pdfbox.rendering.PDFRenderer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

object PdfConverter {
    fun convertImagesToPdf(
        context: Context,
        imageUris: List<Uri>,
        onSuccess: (Uri) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        
        imageUris.forEachIndexed { index, uri ->
            val bitmap = context.contentResolver.openInputStream(uri)?.use { input ->
                android.graphics.BitmapFactory.decodeStream(input)
            } ?: return@forEachIndexed

            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, index + 1).create()
            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)
            bitmap.recycle()
        }

        val outputFile = FileUtils.createPdfFile(context)
        pdfDocument.writeTo(FileOutputStream(outputFile))
        pdfDocument.close()

        onSuccess(Uri.fromFile(outputFile))
    }

    suspend fun convertPdfToImages(
        context: Context,
        pdfUri: Uri,
        onProgress: (Int, Int) -> Unit
    ): List<Uri> = withContext(Dispatchers.IO) {
        PDFBoxResourceLoader.init(context)
        
        val document = context.contentResolver.openInputStream(pdfUri)?.use { input ->
            PDDocument.load(input)
        } ?: return@withContext emptyList()

        val renderer = PDFRenderer(document)
        val totalPages = document.numberOfPages
        val imageUris = mutableListOf<Uri>()

        for (pageIndex in 0 until totalPages) {
            onProgress(pageIndex + 1, totalPages)
            
            val bitmap = renderer.renderImageWithDPI(pageIndex, 300f)
            val imageFile = FileUtils.createImageFile(context) ?: continue
            
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            imageUris.add(Uri.fromFile(imageFile))
            bitmap.recycle()
        }

        document.close()
        imageUris
    }

    suspend fun resizePdf(
        context: Context,
        pdfUri: Uri,
        targetSizeKB: Int,
        onSuccess: (Uri) -> Unit
    ) = withContext(Dispatchers.IO) {
        PDFBoxResourceLoader.init(context)
        
        val document = context.contentResolver.openInputStream(pdfUri)?.use { input ->
            PDDocument.load(input)
        } ?: return@withContext

        val outputFile = FileUtils.createPdfFile(context, "Compressed")
        
        // Create a new document for the compressed version
        val newDocument = PDDocument()
        val renderer = PDFRenderer(document)

        // Process each page
        for (pageIndex in 0 until document.numberOfPages) {
            // Render the page to a bitmap
            val image = renderer.renderImageWithDPI(pageIndex, 150f) // Lower DPI for compression
            
            // Create a new page with the same dimensions
            val page = document.getPage(pageIndex)
            val newPage = PDPage(page.mediaBox)
            newDocument.addPage(newPage)
            
            // Draw the compressed image onto the new page
            PDPageContentStream(newDocument, newPage, PDPageContentStream.AppendMode.APPEND, true).use { contentStream ->
                contentStream.drawImage(
                    LosslessFactory.createFromImage(newDocument, image),
                    0f,
                    0f,
                    page.mediaBox.width,
                    page.mediaBox.height
                )
            }
            
            image.recycle()
        }

        // Save the compressed PDF
        newDocument.save(outputFile)
        newDocument.close()
        document.close()

        // If the file is still too large, try with lower DPI
        if (outputFile.length() > targetSizeKB * 1024L) {
            // Delete the first attempt
            outputFile.delete()
            
            // Try again with lower DPI
            val finalDocument = PDDocument()
            val secondDocument = context.contentResolver.openInputStream(pdfUri)?.use { input ->
                PDDocument.load(input)
            } ?: run {
                finalDocument.close()
                return@withContext
            }
            val renderer2 = PDFRenderer(secondDocument)
            
            for (pageIndex in 0 until secondDocument.numberOfPages) {
                val image = renderer2.renderImageWithDPI(pageIndex, 72f) // Much lower DPI for smaller size
                
                val page = secondDocument.getPage(pageIndex)
                val newPage = PDPage(page.mediaBox)
                finalDocument.addPage(newPage)
                
                PDPageContentStream(finalDocument, newPage, PDPageContentStream.AppendMode.APPEND, true).use { contentStream ->
                    contentStream.drawImage(
                        JPEGFactory.createFromImage(finalDocument, image, 0.5f),
                        0f,
                        0f,
                        page.mediaBox.width,
                        page.mediaBox.height
                    )
                }
                
                image.recycle()
            }
            
            finalDocument.save(outputFile)
            finalDocument.close()
            secondDocument.close()
        }

        onSuccess(Uri.fromFile(outputFile))
    }

    suspend fun resizeImage(
        context: Context,
        imageUri: Uri,
        targetSizeKB: Int,
        onSuccess: (Uri) -> Unit
    ) = withContext(Dispatchers.IO) {
        val bitmap = context.contentResolver.openInputStream(imageUri)?.use { input ->
            android.graphics.BitmapFactory.decodeStream(input)
        } ?: return@withContext

        var quality = 100
        val outputFile = FileUtils.createImageFile(context) ?: return@withContext

        do {
            FileOutputStream(outputFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
            }
            quality -= 10
        } while (outputFile.length() > targetSizeKB * 1024 && quality > 0)

        bitmap.recycle()
        onSuccess(Uri.fromFile(outputFile))
    }
}
