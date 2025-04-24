package com.example.scheduleassistant.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.Earning
import com.example.scheduleassistant.data.models.Expense
import com.example.scheduleassistant.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repo: ExpenseRepository) : ViewModel() {
    val userId: String = repo.getUserId() // Expose userId for use in UI
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()
    private val _earnings = MutableStateFlow<List<Earning>>(emptyList())
    val earnings: StateFlow<List<Earning>> = _earnings.asStateFlow()

    fun loadExpenses() {
        viewModelScope.launch { _expenses.value = repo.getExpenses() }
    }
    fun loadEarnings() {
        viewModelScope.launch { _earnings.value = repo.getEarnings() }
    }
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repo.addExpense(expense)
            loadExpenses()
        }
    }
    fun addEarning(earning: Earning) {
        viewModelScope.launch {
            repo.addEarning(earning)
            loadEarnings()
        }
    }
    fun sync() {
        viewModelScope.launch {
            repo.syncExpensesAndEarnings()
            loadExpenses()
            loadEarnings()
        }
    }
}
