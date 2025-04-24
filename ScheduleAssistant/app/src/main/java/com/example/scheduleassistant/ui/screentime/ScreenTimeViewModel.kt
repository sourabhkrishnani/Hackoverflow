package com.example.scheduleassistant.ui.screentime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.ScreenTimeEntry
import com.example.scheduleassistant.repository.ScreenTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScreenTimeViewModel(private val repo: ScreenTimeRepository) : ViewModel() {
    val userId: String = repo.userId // Expose userId for use in UI
    private val _screenTimeEntries = MutableStateFlow<List<ScreenTimeEntry>>(emptyList())
    val screenTimeEntries: StateFlow<List<ScreenTimeEntry>> = _screenTimeEntries.asStateFlow()

    fun loadScreenTimeEntries() {
        viewModelScope.launch {
            _screenTimeEntries.value = repo.getScreenTimeEntries()
        }
    }

    fun addScreenTimeEntry(entry: ScreenTimeEntry) {
        viewModelScope.launch {
            repo.addScreenTimeEntry(entry)
            loadScreenTimeEntries()
        }
    }

    fun syncScreenTimeEntries() {
        viewModelScope.launch {
            repo.syncScreenTimeEntries()
            loadScreenTimeEntries()
        }
    }
}
