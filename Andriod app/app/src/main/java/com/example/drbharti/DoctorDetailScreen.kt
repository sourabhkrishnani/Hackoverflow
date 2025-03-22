package com.example.drbharti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drbharti.model.Doctor
import com.example.drbharti.ui.theme.DrBhartiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDetailScreen(
    doctorId: String,
    onNavigateBack: () -> Unit = {},
    onStartVideoCall: () -> Unit = {},
    onStartVoiceCall: () -> Unit = {},
    onStartChat: () -> Unit = {}
) {
    val doctor = getDummyDoctors() ?: return
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dr. ${doctor.name}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Doctor profile section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Doctor image placeholder
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Dr. ${doctor.name}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Text(
                        text = doctor.specialty,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    
                    Text(
                        text = "${doctor.rating}",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                
                // Call buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = onStartChat,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.MailOutline,
                            contentDescription = "Chat",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    IconButton(
                        onClick = onStartVoiceCall,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Voice Call",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    IconButton(
                        onClick = onStartVideoCall,
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.Red, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.videocam),
                            contentDescription = "Video Call",
                            tint = Color.White
                        )
                    }
                }
            }
            
            // Doctor information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "About Doctor",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Dr. ${doctor.name} is a highly qualified ${doctor.specialty.lowercase()} with over 10 years of experience. Specializing in treating various conditions related to ${doctor.specialty.lowercase()}.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Available Time Slots",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Time slots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimeSlotChip(
                        time = "9:00 AM",
                        isAvailable = true,
                        onClick = { /* Book this slot */ }
                    )
                    
                    TimeSlotChip(
                        time = "10:30 AM",
                        isAvailable = true,
                        onClick = { /* Book this slot */ }
                    )
                    
                    TimeSlotChip(
                        time = "12:00 PM",
                        isAvailable = false,
                        onClick = { /* Slot not available */ }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimeSlotChip(
                        time = "2:30 PM",
                        isAvailable = true,
                        onClick = { /* Book this slot */ }
                    )
                    
                    TimeSlotChip(
                        time = "4:00 PM",
                        isAvailable = true,
                        onClick = { /* Book this slot */ }
                    )
                    
                    TimeSlotChip(
                        time = "5:30 PM",
                        isAvailable = true,
                        onClick = { /* Book this slot */ }
                    )
                }
            }
        }
    }
}

@Composable
fun getDummyDoctors() : Doctor{

    var doctor : Doctor = Doctor(
        name = "Dr. abc Sharma", rating = 4.8f,
        experience = "15 year",
        patients = TODO(),
        imageUrl = "Image(painter = painterResource(id = R.drawable.doctor_image), contentDescription = \"Doctor image\")",
        location = "Bhopal",
        availableSlots = 23,
    )
}

@Composable
fun TimeSlotChip(
    time: String,
    isAvailable: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isAvailable) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f)
    val textColor = if (isAvailable) MaterialTheme.colorScheme.primary else Color.Gray
    
    Surface(
        modifier = Modifier
            .height(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor,
        onClick = if (isAvailable) onClick else { {} }
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = time,
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorDetailScreenPreview() {
    DrBhartiTheme {
        DoctorDetailScreen(doctorId = "1")
    }
}
