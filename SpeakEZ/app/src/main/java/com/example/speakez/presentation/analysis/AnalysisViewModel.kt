package com.example.speakez.presentation.analysis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakez.data.model.PracticeSession
import com.example.speakez.data.repository.SessionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SessionRepositoryImpl,
) : ViewModel() {

    private val sessionId: Int = checkNotNull(savedStateHandle["sessionId"])

    private val _session = MutableStateFlow<PracticeSession?>(null)
    val session = _session.asStateFlow()

    init {
        viewModelScope.launch {
            _session.value = repository.getSessionById(sessionId)
        }
    }
}
