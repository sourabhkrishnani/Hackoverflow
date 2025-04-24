package com.example.scheduleassistant.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.ScheduleEntry
import com.example.scheduleassistant.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repo: ScheduleRepository) : ViewModel() {
    val userId: String = repo.userId // Expose userId for use in UI
    private val _scheduleEntries = MutableStateFlow<List<ScheduleEntry>>(emptyList())
    val scheduleEntries: StateFlow<List<ScheduleEntry>> = _scheduleEntries.asStateFlow()

    fun loadScheduleEntries() {
        viewModelScope.launch {
            _scheduleEntries.value = repo.getScheduleEntries()
        }
    }

    fun addScheduleEntry(entry: ScheduleEntry) {
        viewModelScope.launch {
            repo.addScheduleEntry(entry)
            loadScheduleEntries()
        }
    }

    fun syncScheduleEntries() {
        viewModelScope.launch {
            repo.syncScheduleEntries()
            loadScheduleEntries()
        }
    }

    fun updateScheduleEntry(entry: ScheduleEntry) {
        viewModelScope.launch {
            repo.updateScheduleEntry(entry)
            loadScheduleEntries()
        }
    }

    fun logScheduleCompletion(entry: ScheduleEntry) {
        viewModelScope.launch {
            repo.logScheduleCompletion(entry)
        }
    }

    fun deleteScheduleEntry(entry: ScheduleEntry) {
        viewModelScope.launch {
            repo.deleteScheduleEntry(entry)
            loadScheduleEntries()
        }
    }
}
