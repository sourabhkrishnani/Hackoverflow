package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drbharti.model.Doctor
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.PrimaryGreen
import com.example.drbharti.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    onDoctorSelected: (String) -> Unit = {},
    viewModel: AppViewModel = viewModel()
) {
    val doctors = viewModel.doctors
    val currentUser = viewModel.currentUser
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Good Morning,",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = currentUser?.name ?: "Bharti!",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                onNavigate = onNavigate,
                currentRoute = "home"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Top Nutritionists",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Featured doctor card
            item {
                FeaturedDoctorCard(
                    doctor = doctors.firstOrNull() ?: Doctor(
                        id = "doc1",
                        name = "Dr. Sara Smith",
                        specialty = "Nutritionist",
                        rating = 4.8f,
                        experience = "10+ years",
                        patients = 1500,
                        availableSlots = 23
                    ),
                    onDoctorSelected = onDoctorSelected
                )
            }
            
            // Previous sessions
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Previous sessions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Previous session doctors
            items(doctors) { doctor ->
                DoctorAppointmentCard(
                    doctor = doctor,
                    onBookClick = { /* Book appointment */ }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for FAB
            }
        }
    }
}

@Composable
fun FeaturedDoctorCard(
    doctor: Doctor,
    onDoctorSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .clickable { onDoctorSelected(doctor.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryGreen
        )
    ) {
        Box(Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {

                    Column {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = doctor.rating.toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            }
                        }
                        Column (modifier = Modifier.padding(top = 8.dp)){
                            Text(
                                text = doctor.specialty,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )

                            Text(
                                text = doctor.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }


                    Spacer(modifier = Modifier.width(20.dp))

//                Image(painter = painterResource(R.drawable.doctor_image_female), contentDescription = "female doctor", contentScale = ContentScale.FillHeight)

                }

                // Available slot text
                Column(
                    modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(6.dp)).background(color = Color.White).background(PrimaryGreen.copy(alpha = 0.2f)).padding(8.dp)
                ) {

                    Text(
                        text = "Available slot 7",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Days of week
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                        val dates = listOf("12", "13", "14", "15", "16", "17", "18")

                        val selectedIndex =  3

                        items(days.size) { index ->
                            DayChip(
                                day = days[index],
                                date = dates[index],
                                isSelected = index == selectedIndex // Thursday selected
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        // Book slot button
                        Button(
//                        onClick = { onDoctorSelected(doctor.id) },
                            onClick = {},
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = PrimaryGreen
                            )
                        ) {
                            Text("BOOK SLOT")
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))

                        // Favorite icon
                        IconButton(onClick = { /* Add to favorites */ }) {
                            var isFilled : Boolean by rememberSaveable {mutableStateOf(true) }
                            Icon(
                                imageVector = if (isFilled){ Icons.Filled.Favorite } else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = Color.Red,
                                modifier = Modifier.clickable(onClick = {isFilled = !isFilled})
                            )
                        }

                    }


                }

            }
            Column (horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()){
                Spacer(Modifier.height(10.dp))
                Surface(shape = RectangleShape, color = Color.Transparent, modifier = Modifier.size(200.dp).clip(RectangleShape)) {
                    Image(painter = painterResource(R.drawable.doctor_image_female), contentDescription = "female doctor", contentScale = ContentScale.FillWidth, alignment = Alignment.TopEnd)
                }
            }




        }

    }
}

@Composable
fun DayChip(
    day: String,
    date: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .size(width = 36.dp, height = 72.dp)
            .clip(CircleShape)
            .background(if (isSelected) PrimaryGreen else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day,
                fontSize = 10.sp,
                color = if (isSelected) Color.White else Color.Gray,
                textAlign = TextAlign.Center
            )
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PreviousSessionCard(
    doctor: Doctor,
    onDoctorSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = {}),
//            .clickable { onDoctorSelected(doctor.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.doctor_placeholder),
                    contentDescription = "Doctor ${doctor.name}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
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
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Slots",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = "2 slots left",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        painter = painterResource(id = R.drawable.analatics_vector),
                        contentDescription = "Experience",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = "${doctor.experience} experience",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            
            Button(
//                onClick = { onDoctorSelected(doctor.id) },
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "Book",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DrBhartiTheme {
        HomeScreen()
    }
}
