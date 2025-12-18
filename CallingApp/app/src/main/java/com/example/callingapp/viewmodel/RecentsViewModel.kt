package com.example.moderncaller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moderncaller.data.CallLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CallLogEntry(
    val name: String?,
    val number: String,
    val date: Long,
    val duration: Long,
    val type: Int
)

data class RecentsUiState(
    val callLogs: List<CallLogEntry> = emptyList(),
    val isLoading: Boolean = true
)

class RecentsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CallLogRepository(application.contentResolver)

    private val _uiState = MutableStateFlow(RecentsUiState())
    val uiState: StateFlow<RecentsUiState> = _uiState.asStateFlow()

    init {
        loadCallLogs()
    }

    private fun loadCallLogs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val logs = repository.getCallLogs()
            _uiState.update { it.copy(callLogs = logs, isLoading = false) }
        }
    }
}
