package com.example.scheduleassistant.ui.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.Habit
import com.example.scheduleassistant.repository.HabitRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitViewModel(private val repo: HabitRepository) : ViewModel() {
    val userId: String = repo.getUserId() // Expose userId for use in UI
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits.asStateFlow()
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()
    private val _syncError = MutableStateFlow<String?>(null)
    val syncError: StateFlow<String?> = _syncError.asStateFlow()

    fun loadHabits() {
        viewModelScope.launch {
            _habits.value = repo.getHabits()
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repo.addHabit(habit)
            loadHabits()
        }
    }

    fun syncHabits() {
        viewModelScope.launch {
            _isSyncing.value = true
            _syncError.value = null
            try {
                withTimeout(10000) { // 10 seconds
                    repo.syncHabits()
                }
                loadHabits()
            } catch (e: TimeoutCancellationException) {
                _syncError.value = "Sync timed out. Please try again."
            } catch (e: Exception) {
                _syncError.value = "Error syncing habits: ${e.localizedMessage ?: e.toString()}"
            } finally {
                _isSyncing.value = false
            }
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repo.updateHabit(habit)
            loadHabits()
        }
    }

    fun logHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            repo.logHabitCompletion(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repo.deleteHabit(habit)
            loadHabits()
        }
    }
}
