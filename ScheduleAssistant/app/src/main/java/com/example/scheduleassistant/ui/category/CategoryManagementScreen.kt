package com.example.scheduleassistant.ui.category

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
import com.example.scheduleassistant.data.models.Category
import com.example.scheduleassistant.ui.theme.MainThColor

@Composable
fun CategoryManagementScreen(viewModel: CategoryViewModel) {
    val categories by viewModel.categories.collectAsState()
    var newCategoryName by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { viewModel.loadCategories() }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Category Management", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = newCategoryName,
                onValueChange = { newCategoryName = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Button(onClick = {
                if (newCategoryName.isNotBlank()) {
                    viewModel.addCategory(
                        Category(
                            id = 0,
                            userId = "", // TODO: Fill with actual userId
                            name = newCategoryName
                        )
                    )
                    newCategoryName = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Category")
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(categories) { category ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(category.name, fontSize = 18.sp, color = MainThColor.TextWhite)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.syncCategories() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}
