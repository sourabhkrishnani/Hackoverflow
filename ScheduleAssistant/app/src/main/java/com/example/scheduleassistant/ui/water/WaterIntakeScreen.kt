package com.example.scheduleassistant.ui.water

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
import com.example.scheduleassistant.data.models.WaterIntake
import com.example.scheduleassistant.ui.theme.BlackBackGround
import com.example.scheduleassistant.ui.theme.MainThColor.TextGrey
import com.example.scheduleassistant.ui.theme.TextWhite

@Composable
fun WaterIntakeScreen(viewModel: WaterViewModel) {
    val waterIntake by viewModel.waterIntake.collectAsState()
    var newAmount by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { viewModel.loadWaterIntake() }
    Box(modifier = Modifier.fillMaxSize().background(BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Water Intake Tracker", fontSize = 32.sp, color = TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newAmount,
                onValueChange = { newAmount = it },
                label = { Text("Amount (ml)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = BlackBackGround, unfocusedTextColor = TextWhite)
            )
            Button(onClick = {
                if (newAmount.isNotBlank()) {
                    viewModel.addWaterIntake(
                        WaterIntake(
                            id = 0,
                            userId = viewModel.userId, // Use actual userId from ViewModel
                            amountMl = newAmount.toIntOrNull() ?: 0
                        )
                    )
                    newAmount = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Water Intake")
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(waterIntake) { entry ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text("Amount: ${entry.amountMl} ml", fontSize = 18.sp, color = TextWhite)
                            Text("Date: ${entry.date}", color = TextGrey)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncWaterIntake() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}
