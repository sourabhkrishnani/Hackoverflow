package com.example.drbharti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.drbharti.model.Doctor
import com.example.drbharti.model.Article
import com.example.drbharti.model.User
import com.example.drbharti.model.HealthMetric

class AppViewModel : ViewModel() {
    
    // User state
    var currentUser by mutableStateOf<User?>(null)
        private set
    
    // Authentication state
    var isLoggedIn by mutableStateOf(false)
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    var errorMessage by mutableStateOf<String?>(null)
        private set
    
    // Registration state
    var registrationData by mutableStateOf(RegistrationData())
        private set
    
    // Home screen state
    var doctors by mutableStateOf<List<Doctor>>(emptyList())
        private set
    
    var featuredArticles by mutableStateOf<List<Article>>(emptyList())
        private set
    
    // Progress screen state
    var healthMetrics by mutableStateOf<List<HealthMetric>>(emptyList())
        private set
    
    init {
        // Load mock data for demonstration
        loadMockData()
    }
    
    // Authentication methods
    fun login(email: String, password: String) {
        isLoading = true
        errorMessage = null
        
        // Simulate API call
        try {
            // For demo purposes, any non-empty credentials work
            if (email.isNotEmpty() && password.isNotEmpty()) {
                currentUser = User(
                    id = "user123",
                    name = "John Doe",
                    email = email,
                    profilePicture = null
                )
                isLoggedIn = true
            } else {
                errorMessage = "Invalid credentials"
            }
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }
    
    fun loginWithGoogle() {
        isLoading = true
        errorMessage = null
        
        try {
            // Simulate Google login
            currentUser = User(
                id = "google123",
                name = "Google User",
                email = "google@example.com",
                profilePicture = null
            )
            isLoggedIn = true
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }
    
    fun logout() {
        currentUser = null
        isLoggedIn = false
    }
    
    // Registration methods
    fun updateRegistrationData(
        username: String? = null,
        email: String? = null,
        password: String? = null,
        name: String? = null,
        gender: String? = null,
        dateOfBirth: String? = null,
        age: String? = null,
        height: String? = null,
        weight: String? = null,
        allergies: String? = null
    ) {
        registrationData = registrationData.copy(
            username = username ?: registrationData.username,
            email = email ?: registrationData.email,
            password = password ?: registrationData.password,
            name = name ?: registrationData.name,
            gender = gender ?: registrationData.gender,
            dateOfBirth = dateOfBirth ?: registrationData.dateOfBirth,
            age = age ?: registrationData.age,
            height = height ?: registrationData.height,
            weight = weight ?: registrationData.weight,
            allergies = allergies ?: registrationData.allergies
        )
    }
    
    fun updateRegistrationData(data: RegistrationData) {
        registrationData = data
    }
    
    fun register() {
        isLoading = true
        errorMessage = null
        
        try {
            // Simulate registration API call
            if (registrationData.email.isNotEmpty() && registrationData.password.isNotEmpty()) {
                // Create user with all the collected details
                currentUser = User(
                    id = "newuser123",
                    name = registrationData.name.ifEmpty { registrationData.username },
                    email = registrationData.email,
                    profilePicture = null,
                    gender = registrationData.gender,
                    dateOfBirth = registrationData.dateOfBirth,
                    age = registrationData.age,
                    height = registrationData.height,
                    weight = registrationData.weight,
                    allergies = registrationData.allergies
                )
                isLoggedIn = true
                navigateToNextScreen()
            } else {
                errorMessage = "Please fill all required fields"
            }
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }
    
    fun navigateToNextScreen() {
        // After successful registration, navigate to home screen
        // This method will be called after registration is complete
    }

    fun clearRegistrationData() {
        registrationData = RegistrationData()
    }
    
    // Doctor methods
    fun getDoctorById(doctorId: String): Doctor? {
        return doctors.find { it.id == doctorId }
    }
    
    // Health metrics methods
    fun updateHealthMetric(metric: HealthMetric) {
        val updatedMetrics = healthMetrics.toMutableList()
        val index = updatedMetrics.indexOfFirst { it.id == metric.id }
        
        if (index != -1) {
            updatedMetrics[index] = metric
        } else {
            updatedMetrics.add(metric)
        }
        
        healthMetrics = updatedMetrics
    }
    
    // Mock data for demonstration
    private fun loadMockData() {
        // Mock doctors
        doctors = listOf(
            Doctor(
                id = "doc1",
                name = "Dr. Sarah Johnson",
                rating = 4.8f,
                experience = "15 years",
                patients = 1500,
                imageUrl = null,
                availableSlots = 23
            ),
            Doctor(
                id = "doc2",
                name = "Dr. Michael Chen",
                specialty = "Neurologist",
                rating = 4.7f,
                experience = "12 years",
                patients = 1200,
                imageUrl = null,
                availableSlots = 23
            ),
            Doctor(
                id = "doc3",
                name = "Dr. Emily Williams",
                specialty = "Pediatrician",
                rating = 4.9f,
                experience = "10 years",
                patients = 2000,
                imageUrl = null,
                availableSlots = 23
            )
        )
        
        // Mock articles
        featuredArticles = listOf(
            Article(
                id = "art1",
                title = "Understanding Heart Health",
                category = "Cardiology",
                author = "Dr. Sarah Johnson",
                date = "March 15, 2025",
                imageUrl = null
            ),
            Article(
                id = "art2",
                title = "Nutrition Tips for Children",
                category = "Pediatrics",
                author = "Dr. Emily Williams",
                date = "March 10, 2025",
                imageUrl = null
            ),
            Article(
                id = "art3",
                title = "Managing Stress and Brain Health",
                category = "Neurology",
                author = "Dr. Michael Chen",
                date = "March 5, 2025",
                imageUrl = null
            )
        )
        
        // Mock health metrics
        healthMetrics = listOf(
            HealthMetric(
                id = "water",
                name = "Water Intake",
                value = 4,
                target = 8,
                unit = "glasses"
            ),
            HealthMetric(
                id = "meals",
                name = "Healthy Meals",
                value = 2,
                target = 3,
                unit = "meals"
            ),
            HealthMetric(
                id = "sleep",
                name = "Sleep",
                value = 6,
                target = 8,
                unit = "hours"
            )
        )
    }
}

// Data class for registration form
data class RegistrationData(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val gender: String = "Female",
    val dateOfBirth: String = "",
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val allergies: String = ""
)
