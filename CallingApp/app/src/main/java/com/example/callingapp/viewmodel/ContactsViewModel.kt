package com.example.callingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moderncaller.data.Contact
import com.example.moderncaller.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = true
)

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContactRepository(application.contentResolver)

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val contacts = repository.getContacts()
            _uiState.update { it.copy(contacts = contacts, isLoading = false) }
        }
    }
}
