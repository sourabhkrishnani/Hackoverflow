package com.example.scheduleassistant.ui.expense

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
import com.example.scheduleassistant.data.models.Expense
import com.example.scheduleassistant.data.models.Earning
import com.example.scheduleassistant.ui.theme.MainThColor
import java.util.Date

@Composable
fun ExpenseEarningsScreen(viewModel: ExpenseViewModel) {
    val expenses by viewModel.expenses.collectAsState()
    val earnings by viewModel.earnings.collectAsState()
    var newExpenseName by remember { mutableStateOf("") }
    var newExpenseAmount by remember { mutableStateOf("") }
    var newEarningName by remember { mutableStateOf("") }
    var newEarningAmount by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.loadExpenses()
        viewModel.loadEarnings()
    }
    Box(modifier = Modifier.fillMaxSize().background(MainThColor.BlackBackGround)) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Expenses & Earnings", fontSize = 32.sp, color = MainThColor.TextWhite)
            Spacer(Modifier.height(16.dp))
            Text("Add Expense", color = MainThColor.TextWhite)
            OutlinedTextField(
                value = newExpenseName,
                onValueChange = { newExpenseName = it },
                label = { Text("Expense Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newExpenseAmount,
                onValueChange = { newExpenseAmount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Button(onClick = {
                if (newExpenseName.isNotBlank() && newExpenseAmount.isNotBlank()) {
                    viewModel.addExpense(
                        Expense(
                            id = 0,
                            userId = viewModel.userId,
                            amount = newExpenseAmount.toDoubleOrNull() ?: 0.0,
                            description = newExpenseName,
                            categoryId = null,
                            date = Date()
                        )
                    )
                    newExpenseName = ""
                    newExpenseAmount = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Expense")
            }
            Spacer(Modifier.height(16.dp))
            Text("Add Earning", color = MainThColor.TextWhite)
            OutlinedTextField(
                value = newEarningName,
                onValueChange = { newEarningName = it },
                label = { Text("Earning Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            OutlinedTextField(
                value = newEarningAmount,
                onValueChange = { newEarningAmount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = MainThColor.BlackBackGround, unfocusedTextColor = MainThColor.TextWhite)
            )
            Button(onClick = {
                if (newEarningName.isNotBlank() && newEarningAmount.isNotBlank()) {
                    viewModel.addEarning(
                        Earning(
                            id = 0,
                            userId = viewModel.userId,
                            amount = newEarningAmount.toDoubleOrNull() ?: 0.0,
                            description = newEarningName,
                            categoryId = null,
                            date = Date()
                        )
                    )
                    newEarningName = ""
                    newEarningAmount = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Earning")
            }
            Spacer(Modifier.height(16.dp))
            Text("Expenses", color = MainThColor.TextWhite)
            LazyColumn(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                items(expenses) { expense ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(expense.description ?: "", fontSize = 18.sp, color = MainThColor.TextWhite)
                            Text("Amount: ${expense.amount}", color = MainThColor.TextGrey)
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("Earnings", color = MainThColor.TextWhite)
            LazyColumn(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                items(earnings) { earning ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(earning.description ?: "", fontSize = 18.sp, color = MainThColor.TextWhite)
                            Text("Amount: ${earning.amount}", color = MainThColor.TextGrey)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.sync() }, modifier = Modifier.fillMaxWidth()) {
                Text("Sync with Server")
            }
        }
    }
}
