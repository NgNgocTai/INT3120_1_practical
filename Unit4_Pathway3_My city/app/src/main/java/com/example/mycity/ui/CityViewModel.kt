package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import com.example.mycity.data.DataSource
import com.example.mycity.data.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CityUiState())
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    init {
        val categories = DataSource.categories
        _uiState.value = CityUiState(
            categories = categories,
            currentCategory = categories.firstOrNull(),
            currentRecommendation = categories.firstOrNull()?.recommendations?.firstOrNull()
        )
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = category,
                currentRecommendation = category.recommendations.firstOrNull()
            )
        }
    }

    fun updateCurrentRecommendation(recommendation: Recommendation) {
        _uiState.update { currentState ->
            currentState.copy(
                currentRecommendation = recommendation
            )
        }
    }
}