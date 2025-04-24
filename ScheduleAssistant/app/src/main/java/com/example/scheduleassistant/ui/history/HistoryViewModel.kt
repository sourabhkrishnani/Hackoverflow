package com.example.scheduleassistant.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.HistoryEntry
import com.example.scheduleassistant.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val repo: HistoryRepository) : ViewModel() {
    private val _historyEntries = MutableStateFlow<List<HistoryEntry>>(emptyList())
    val historyEntries: StateFlow<List<HistoryEntry>> = _historyEntries.asStateFlow()

    fun loadHistoryEntries() {
        viewModelScope.launch {
            _historyEntries.value = repo.getHistoryEntries()
        }
    }

    fun addHistoryEntry(entry: HistoryEntry) {
        viewModelScope.launch {
            repo.addHistoryEntry(entry)
            loadHistoryEntries()
        }
    }

    fun syncHistoryEntries() {
        viewModelScope.launch {
            repo.syncHistoryEntries()
            loadHistoryEntries()
        }
    }
}
