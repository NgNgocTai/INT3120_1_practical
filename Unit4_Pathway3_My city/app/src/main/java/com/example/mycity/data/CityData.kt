package com.example.mycity.data

import androidx.annotation.DrawableRes

// Định nghĩa cấu trúc cho một địa điểm đề xuất
data class Recommendation(
    val id: Int,
    val title: String,
    val description: String,
    val address: String,
    @DrawableRes val imageResId: Int
)

// Định nghĩa cấu trúc cho một danh mục
data class Category(
    val id: Int,
    val name: String,
    val recommendations: List<Recommendation>
)