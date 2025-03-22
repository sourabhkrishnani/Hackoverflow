package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drbharti.ui.theme.PrimaryGreen

@Preview
@Composable
fun NavigationBar(
    onNavigate: (String) -> Unit = {},
    currentRoute: String = "home"
) {
    // Initialize selectedItem with the currentRoute
    var selectedItem by remember { mutableStateOf(currentRoute) }
    
    // Update selectedItem when currentRoute changes
    DisposableEffect(currentRoute) {
        selectedItem = currentRoute
        onDispose { }
    }
    
    NavigationBar (containerColor = PrimaryGreen.copy(alpha = 0.2f)){
        // Articles
        NavigationBarItem(
            selected = selectedItem == "articles",
            onClick = { 
                selectedItem = "articles"
                onNavigate("articles") 
            },
            icon = { 
                Icon(
                    painter = painterResource(id = R.drawable.article_vector),
                    contentDescription = "Articles",
                    tint = if (selectedItem == "articles") PrimaryGreen else Color.Gray,
                ) 
            },
            label = { Text("Articles", color = if (selectedItem == "articles") PrimaryGreen else Color.Gray, fontSize = 10.sp) }
        )
        
        // Community
        NavigationBarItem(
            selected = selectedItem == "communities",
            onClick = { 
                selectedItem = "communities"
                onNavigate("communities") 
            },
            icon = { 
                Icon(
                    painter = painterResource(id = R.drawable.community_vector),
//                    imageVector = Icons.Outlined.,
                    contentDescription = "Community",
                    tint = if (selectedItem == "communities") PrimaryGreen else Color.Gray,
                    modifier = Modifier.size(24.dp)
                ) 
            },
            label = { Text("Community", color = if (selectedItem == "communities") PrimaryGreen else Color.Gray, fontSize = 10.sp) }
        )

        // Home
        NavigationBarItem(
            selected = selectedItem == "home",
            onClick = {
                selectedItem = "home"
                onNavigate("home")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedItem == "home") PrimaryGreen else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Home", color = if (selectedItem == "home") PrimaryGreen else Color.Gray, fontSize = 10.sp) }
        )
        
        // Appointment
        NavigationBarItem(
            selected = selectedItem == "appointment",
            onClick = { 
                selectedItem = "appointment"
                onNavigate("appointment")
            },
            icon = { 
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Appointment",
                    tint = if (selectedItem == "appointment") PrimaryGreen else Color.Gray,
                    modifier = Modifier.size(24.dp)
                ) 
            },
            label = { Text("Appointment", color = if (selectedItem == "appointment") PrimaryGreen else Color.Gray, fontSize = 9.sp) }
        )
        
        // Progress
        NavigationBarItem(
            selected = selectedItem == "progress",
            onClick = { 
                selectedItem = "progress"
                onNavigate("progress") 
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.progress_icon),
                    contentDescription = "Progress",
                    modifier = Modifier.size(24.dp)
                ) 
            },
            label = { Text("Progress" , color = if (selectedItem == "progress") PrimaryGreen else Color.Gray, fontSize = 10.sp) }
        )
    }
}