// CityUiState.kt
package com.example.mycity.ui

import com.example.mycity.data.Category
import com.example.mycity.data.Recommendation

// Data class này giữ toàn bộ trạng thái của ứng dụng
data class CityUiState(
    val categories: List<Category> = emptyList(),
    val currentCategory: Category? = null,
    val currentRecommendation: Recommendation? = null
)