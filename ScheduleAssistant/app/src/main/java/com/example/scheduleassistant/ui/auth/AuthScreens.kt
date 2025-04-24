package com.example.scheduleassistant.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.ui.theme.MainThColor
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, onNavigateToSignup: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(24.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(16.dp))
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    onLogin(email, password)
                } else {
                    error = "Email and password cannot be empty."
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onNavigateToSignup) {
                Text("Don't have an account? Sign up", color = MainThColor.ActiveRedOrange)
            }
            // TEMPORARY TEST LOGIN BUTTON - REMOVE IN FINAL VERSION
            Button(
                onClick = {
                    // Fill fields for visibility
                    email = "testuser@example.com"
                    password = "testpassword"
                    // Save a fake token and userId to SharedPreferences for instant login
                    context.applicationContext.getSharedPreferences("auth_prefs", 0)
                        .edit().putString("token", "FAKE_TOKEN_FOR_TEST").putString("userId", "test_user_id").apply()
                    // Immediately navigate to the main/logged-in screen (simulate login)
                    onLogin("testuser@example.com", "testpassword")
                    error = "Test login triggered. App should now act as logged in."
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainThColor.ActiveRedOrange)
            ) {
                Text("Test Login", color = MainThColor.TextWhite)
            }
            // END TEMPORARY BUTTON
        }
    }
}

@Composable
fun SignupScreen(onSignup: (String, String, String) -> Unit, onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sign Up", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(24.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Spacer(Modifier.height(16.dp))
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank()) {
                    onSignup(email, password, name)
                } else {
                    error = "All fields are required."
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Sign Up")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onNavigateToLogin) {
                Text("Already have an account? Log in", color = MainThColor.ActiveRedOrange)
            }
        }
    }
}
