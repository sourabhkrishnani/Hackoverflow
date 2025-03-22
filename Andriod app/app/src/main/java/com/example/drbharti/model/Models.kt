package com.example.drbharti.model

import android.media.Image

// User model
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String? = null,
    val gender: String = "",
    val dateOfBirth: String = "",
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val allergies: String = ""
)

// Doctor model
data class Doctor(
    val id: String = "D01",
    val name: String = "Dr. ABC Sharma",
    val specialty: String = "Cardiologist",
    val rating: Float = 4.5f,
    val experience: String = "9 Years",
    val patients: Int = 10,
    val imageUrl: String? = null,
    val description: String = "Experienced healthcare professional specializing in $specialty, dedicated to providing high-quality patient care.",
    val availability: List<String> = listOf("Monday", "Wednesday", "Friday"),
    val location: String = "Main Medical Center, 123 Health St.",
    val availableSlots: Int
)

// Article model
data class Article(
    val id: String,
    val title: String,
    val category: String,
    val author: String,
    val date: String,
    val imageUrl: String? = null,
    val content: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
)

// Health metric model for progress tracking
data class HealthMetric(
    val id: String,
    val name: String,
    val value: Int,
    val target: Int,
    val unit: String
) {
    val progress: Float
        get() = value.toFloat() / target.toFloat()
}

fun getDoctorImage(){

}
