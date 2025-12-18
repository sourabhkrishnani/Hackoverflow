package com.example.callingapp.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialpadScreen() {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display for the phone number
        Text(
            text = phoneNumber,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Dialpad Grid
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttons = listOf(
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "*", "0", "#"
            )
            buttons.chunked(3).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    row.forEach { digit ->
                        DialButton(text = digit, onClick = { phoneNumber += digit })
                    }
                }
            }
        }


        // Call and Backspace buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(64.dp))
            FloatingActionButton(
                onClick = {
                    if (phoneNumber.isNotBlank()) {
                        makeCall(context, phoneNumber)
                    } else {
                        Toast.makeText(context, "Enter a number", Toast.LENGTH_SHORT).show()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Call, contentDescription = "Call")
            }
            IconButton(onClick = {
                if (phoneNumber.isNotEmpty()) {
                    phoneNumber = phoneNumber.dropLast(1)
                }
            }) {
                Icon(
                    Icons.Filled.Backspace,
                    contentDescription = "Backspace",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

    }
}

@Composable
fun DialButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(72.dp),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text, fontSize = 24.sp)
    }
}

private fun makeCall(context: Context, number: String) {
    try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        context.startActivity(intent)
    } catch (e: SecurityException) {
        Toast.makeText(context, "Permission to call not granted", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun DialpadScreenPreview() {
    DialpadScreen()
}
