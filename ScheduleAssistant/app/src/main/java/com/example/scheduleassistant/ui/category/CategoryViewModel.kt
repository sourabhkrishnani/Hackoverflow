package com.example.scheduleassistant.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleassistant.data.models.Category
import com.example.scheduleassistant.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repo: CategoryRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = repo.getCategories()
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            repo.addCategory(category)
            loadCategories()
        }
    }

    fun syncCategories() {
        viewModelScope.launch {
            repo.syncCategories()
            loadCategories()
        }
    }
}
