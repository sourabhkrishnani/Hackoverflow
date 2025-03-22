package com.example.drbharti

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.PrimaryGreen
import com.example.drbharti.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutrientsIntakeScreen(
    onNavigateBack: () -> Unit,
    onSubmit: () -> Unit,
    viewModel: AppViewModel = viewModel()
){
    var breakfast by remember { mutableStateOf("") }
    var lunch by remember { mutableStateOf("") }
    var snacks by remember { mutableStateOf("") }
    var dinner by remember { mutableStateOf("") }
    
    // State to store entered items
    var enteredItems by remember { mutableStateOf(mapOf(
        "breakfast" to emptyList<String>(),
        "lunch" to emptyList<String>(),
        "snacks" to emptyList<String>(),
        "dinner" to emptyList<String>()
    )) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Back Arrow", 
                            modifier = Modifier.clickable { onNavigateBack() },
                            tint = Color.White
                        )
                        Spacer(Modifier.width(16.dp))
                        Text("My Nutrients", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
                    titleContentColor = Color.White
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            // Input Fields
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ){
                Text(text = "Add Breakfast", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                TextField(
                    value = breakfast, 
                    onValueChange = { 
                        breakfast = it
                        // Add item to list when user presses enter
                        if (it.endsWith("\n")) {
                            val newItem = it.trimEnd('\n')
                            if (newItem.isNotEmpty()) {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["breakfast"] = this["breakfast"]!!.toMutableList().apply {
                                        add(newItem)
                                    }
                                }
                                breakfast = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, RectangleShape)
                        .padding(vertical = 8.dp)
                )

                Text(text = "Add Lunch", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                TextField(
                    value = lunch, 
                    onValueChange = { 
                        lunch = it
                        if (it.endsWith("\n")) {
                            val newItem = it.trimEnd('\n')
                            if (newItem.isNotEmpty()) {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["lunch"] = this["lunch"]!!.toMutableList().apply {
                                        add(newItem)
                                    }
                                }
                                lunch = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, RectangleShape)
                        .padding(vertical = 8.dp)
                )

                Text(text = "Add Snacks", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                TextField(
                    value = snacks, 
                    onValueChange = { 
                        snacks = it
                        if (it.endsWith("\n")) {
                            val newItem = it.trimEnd('\n')
                            if (newItem.isNotEmpty()) {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["snacks"] = this["snacks"]!!.toMutableList().apply {
                                        add(newItem)
                                    }
                                }
                                snacks = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, RectangleShape)
                        .padding(vertical = 8.dp)
                )

                Text(text = "Add Dinner", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                TextField(
                    value = dinner, 
                    onValueChange = { 
                        dinner = it
                        if (it.endsWith("\n")) {
                            val newItem = it.trimEnd('\n')
                            if (newItem.isNotEmpty()) {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["dinner"] = this["dinner"]!!.toMutableList().apply {
                                        add(newItem)
                                    }
                                }
                                dinner = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, RectangleShape)
                        .padding(vertical = 8.dp)
                )
            }
            
            // Display entered items
            Text(text = "Your Food Intake", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            // Breakfast items
            if (enteredItems["breakfast"]!!.isNotEmpty()) {
                Text(text = "Breakfast", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                enteredItems["breakfast"]!!.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Handle item click (edit)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["breakfast"] = this["breakfast"]!!.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete item",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            
            // Lunch items
            if (enteredItems["lunch"]!!.isNotEmpty()) {
                Text(text = "Lunch", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                enteredItems["lunch"]!!.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Handle item click (edit)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["lunch"] = this["lunch"]!!.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete item",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            
            // Snacks items
            if (enteredItems["snacks"]!!.isNotEmpty()) {
                Text(text = "Snacks", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                enteredItems["snacks"]!!.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Handle item click (edit)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["snacks"] = this["snacks"]!!.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete item",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            
            // Dinner items
            if (enteredItems["dinner"]!!.isNotEmpty()) {
                Text(text = "Dinner", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                enteredItems["dinner"]!!.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                // Handle item click (edit)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                enteredItems = enteredItems.toMutableMap().apply {
                                    this["dinner"] = this["dinner"]!!.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete item",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
            
            Column (verticalArrangement = Arrangement.Bottom, 
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ){
                Row (horizontalArrangement = Arrangement.SpaceEvenly, 
                    verticalAlignment = Alignment.CenterVertically, 
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ){
                    OutlinedButton(
                        onClick = { onNavigateBack() },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen
                        ),
                    ){
                        Text("Skip", fontWeight = FontWeight.Medium)
                    }

                    OutlinedButton(
                        onClick = { onSubmit() },
                        border = BorderStroke(1.dp, PrimaryGreen),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            containerColor = PrimaryGreen
                        ),
                    ){
                        Text("Done", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutrientsIntakeScreenPreview() {
    DrBhartiTheme {
        NutrientsIntakeScreen(
            onNavigateBack = {},
            onSubmit = {}
        )
    }
}