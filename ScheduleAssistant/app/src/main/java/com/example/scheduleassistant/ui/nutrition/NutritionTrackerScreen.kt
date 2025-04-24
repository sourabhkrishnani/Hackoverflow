package com.example.scheduleassistant.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleassistant.data.models.NutritionEntry
import com.example.scheduleassistant.ui.theme.MainThColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NutritionTrackerScreen(viewModel: NutritionViewModel) {
    val nutritionEntries by viewModel.nutritionEntries.collectAsState()
    var newFoodName by remember { mutableStateOf("") }
    var newPortionSize by remember { mutableStateOf("") }
    var newCalories by remember { mutableStateOf("") }
    var newNutrients by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { viewModel.loadNutritionEntries() }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nutrition Tracker", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newFoodName,
                onValueChange = { newFoodName = it },
                label = { Text("Food Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newPortionSize,
                onValueChange = { newPortionSize = it },
                label = { Text("Portion Size") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newCalories,
                onValueChange = { newCalories = it },
                label = { Text("Calories") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newNutrients,
                onValueChange = { newNutrients = it },
                label = { Text("Nutrients (optional)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newDate,
                onValueChange = { newDate = it },
                label = { Text("Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Button(onClick = {
                if (newFoodName.isNotBlank() && newCalories.isNotBlank() && newPortionSize.isNotBlank()) {
                    val parsedDate = try {
                        SimpleDateFormat("yyyy-MM-dd").parse(newDate)
                    } catch (e: Exception) {
                        null
                    }
                    viewModel.addNutritionEntry(
                        NutritionEntry(
                            id = 0,
                            userId = viewModel.userId,
                            foodName = newFoodName,
                            portionSize = newPortionSize,
                            calories = newCalories.toIntOrNull() ?: 0,
                            nutrients = newNutrients.takeIf { it.isNotBlank() },
                            date = parsedDate ?: Date()
                        )
                    )
                    newFoodName = ""
                    newPortionSize = ""
                    newCalories = ""
                    newNutrients = ""
                    newDate = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Entry")
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(nutritionEntries) { entry ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(entry.foodName, fontSize = 18.sp, color = MainThColor.TextWhite)
                            Text("Portion Size: ${entry.portionSize}", color = MainThColor.TextGrey)
                            Text("Calories: ${entry.calories}", color = MainThColor.TextGrey)
                            Text("Nutrients: ${entry.nutrients ?: "N/A"}", color = MainThColor.TextGrey)
                            Text("Date: ${entry.date}", color = MainThColor.TextGrey)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncNutritionEntries() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}
