package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mycity.data.Category
import com.example.mycity.data.Recommendation

/**
 * Màn hình hiển thị danh sách các danh mục.
 */
@Composable
fun CategoryListScreen(
    categories: List<Category>,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(8.dp)) {
        items(categories) { category ->
            ListItemCard(
                text = category.name,
                onItemClick = { onItemClick(category) },
            )
        }
    }
}

/**
 * Màn hình hiển thị danh sách các địa điểm trong một danh mục đã chọn.
 */
@Composable
fun RecommendationsListScreen(
    recommendations: List<Recommendation>,
    onItemClick: (Recommendation) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(8.dp)) {
        items(recommendations) { recommendation ->
            ListItemCard(
                text = recommendation.title,
                onItemClick = { onItemClick(recommendation) }
            )
        }
    }
}

/**
 * Một Card có thể tái sử dụng để hiển thị một mục trong danh sách.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemCard(
    text: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Màn hình hiển thị thông tin chi tiết của một địa điểm.
 */
@Composable
fun RecommendationDetailScreen(
    recommendation: Recommendation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = recommendation.imageResId),
            contentDescription = recommendation.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = recommendation.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = recommendation.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Địa chỉ: ${recommendation.address}", style = MaterialTheme.typography.bodyMedium)
    }
}