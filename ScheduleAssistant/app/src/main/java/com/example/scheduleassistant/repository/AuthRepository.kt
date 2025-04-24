package com.example.scheduleassistant.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.scheduleassistant.BuildConfig
import com.example.scheduleassistant.network.ApiService
import com.example.scheduleassistant.network.AuthRequest
import com.example.scheduleassistant.network.AuthResponse
import com.example.scheduleassistant.network.SignupRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val api: ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL as String)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun saveToken(token: String, userId: String) {
        prefs.edit().putString("token", token).putString("userId", userId).apply()
    }
    fun getToken(): String? = prefs.getString("token", null)
    fun getUserId(): String? = prefs.getString("userId", null)
    fun clearSession() { prefs.edit().clear().apply() }

    suspend fun login(email: String, password: String): AuthResponse? {
        return try {
            val response = api.login(AuthRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { saveToken(it.token, it.userId) }
                response.body()
            } else {
                // Optionally log error body for debugging
                null
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log error to logcat
            null
        }
    }

    suspend fun signup(email: String, password: String, name: String): AuthResponse? {
        return try {
            val response = api.signup(SignupRequest(email, password, name))
            if (response.isSuccessful) {
                response.body()?.let { saveToken(it.token, it.userId) }
                response.body()
            } else {
                // Optionally log error body for debugging
                null
            }
        } catch (e: Exception) {
            e.printStackTrace() // Log error to logcat
            null
        }
    }
}
