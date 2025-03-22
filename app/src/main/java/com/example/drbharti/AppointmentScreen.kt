package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drbharti.model.Doctor
import com.example.drbharti.model.getDoctorImage
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.PrimaryGreen
import com.example.drbharti.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AppViewModel = viewModel()
) {
    // Sample doctors data
    val doctors = remember {
        listOf(
            Doctor(
                id = "1",
                name = "Dr. abc sharma",
                specialty = "Nutritionist",
                experience = "15+ years experience",
                availableSlots = 2,
                imageUrl = "null"
            ),
            Doctor(
                id = "2",
                name = "Dr. abc sharma",
                specialty = "Nutritionist",
                experience = "15+ years experience",
                availableSlots = 23,
                imageUrl = "null"
            ),
            Doctor(
                id = "3",
                name = "Dr. abc sharma",
                specialty = "Nutritionist",
                experience = "15+ years experience",
                availableSlots = 23,
                imageUrl = "null"
            ),
            Doctor(
                id = "4",
                name = "Dr. abc sharma",
                specialty = "Nutritionist",
                experience = "15+ years experience",
                availableSlots = 23,
                imageUrl = "null"
            )
        )
    }
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                onNavigate = onNavigate,
                currentRoute = "appointment"
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)){


            var selectedCategory : String by rememberSaveable { mutableStateOf("All") }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Doctors Nearby",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                CategoryChip(
                    name = "All",
                    isSelected = selectedCategory == "All",
                    onClick = { selectedCategory = "All" }
                )

                CategoryChip(
                    name = "Nutritionist",
                    isSelected = selectedCategory == "Nutritionist",
                    onClick = { selectedCategory = "Nutritionist" }
                )

                CategoryChip(
                    name = "Cardiologist",
                    isSelected = selectedCategory == "Cardiologist",
                    onClick = { selectedCategory = "Cardiologist" }
                )

                CategoryChip(
                    name = "Pediatrician",
                    isSelected = selectedCategory == "Pediatrician",
                    onClick = { selectedCategory = "Pediatrician" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(doctors) { doctor ->
                    DoctorAppointmentCard(
                        doctor = doctor,
                        onBookClick = { /* Book appointment */ }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun DoctorAppointmentCard(
    doctor: Doctor,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Doctor image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (doctor.imageUrl != null) {
                    Image(
                        painter = painterResource(id = R.drawable.doctor_image),
                        contentDescription = "Doctor ${doctor.name}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.doctor_placeholder),
                        contentDescription = "Doctor placeholder",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = doctor.specialty,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = "Slots",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${doctor.availableSlots} slots left",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }

                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Experience",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = doctor.experience,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

            }

            
            Button(
                onClick = onBookClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(36.dp)
            ) {
                Text("Book")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentScreenPreview() {
    DrBhartiTheme {
        AppointmentScreen()
    }
}
