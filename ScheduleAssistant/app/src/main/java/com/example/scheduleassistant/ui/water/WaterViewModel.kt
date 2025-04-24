package com.example.scheduleassistant.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.WaterIntake
import com.example.scheduleassistant.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WaterViewModel(private val repo: WaterIntakeRepository) : ViewModel() {
    val userId: String = repo.userId // Expose userId for use in UI
    private val _waterIntake = MutableStateFlow<List<WaterIntake>>(emptyList())
    val waterIntake: StateFlow<List<WaterIntake>> = _waterIntake.asStateFlow()

    fun loadWaterIntake() {
        viewModelScope.launch {
            _waterIntake.value = repo.getWaterIntake()
        }
    }

    fun addWaterIntake(water: WaterIntake) {
        viewModelScope.launch {
            repo.addWaterIntake(water)
            loadWaterIntake()
        }
    }

    fun syncWaterIntake() {
        viewModelScope.launch {
            repo.syncWaterIntake()
            loadWaterIntake()
        }
    }
}
