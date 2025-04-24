package com.example.scheduleassistant.ui.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.NutritionEntry
import com.example.scheduleassistant.repository.NutritionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NutritionViewModel(private val repo: NutritionRepository) : ViewModel() {
    val userId: String = repo.  userId // Expose userId for use in UI
    private val _nutritionEntries = MutableStateFlow<List<NutritionEntry>>(emptyList())
    val nutritionEntries: StateFlow<List<NutritionEntry>> = _nutritionEntries.asStateFlow()

    fun loadNutritionEntries() {
        viewModelScope.launch {
            _nutritionEntries.value = repo.getNutritionEntries()
        }
    }

    fun addNutritionEntry(entry: NutritionEntry) {
        viewModelScope.launch {
            repo.addNutritionEntry(entry)
            loadNutritionEntries()
        }
    }

    fun syncNutritionEntries() {
        viewModelScope.launch {
            repo.syncNutritionEntries()
            loadNutritionEntries()
        }
    }
}
