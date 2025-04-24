package com.example.mydocument.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ImageToPdf : Screen("image_to_pdf")
    object ResizeImage : Screen("resize_image")
    object PdfToImage : Screen("pdf_to_image")
    object PdfReducer : Screen("pdf_reducer")
}
