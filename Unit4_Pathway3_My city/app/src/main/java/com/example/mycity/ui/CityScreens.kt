package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
 * Màn hình danh sách các danh mục
 */
@Composable
fun CategoryListScreen(
    categories: List<Category>,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            ListItemCard(
                text = category.name,
                onItemClick = { onItemClick(category) },
            )
        }
    }
}

/**
 * Màn hình danh sách các địa điểm, đã thêm padding.
 */
@Composable
fun RecommendationsListScreen(
    recommendations: List<Recommendation>,
    onItemClick: (Recommendation) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recommendations) { recommendation ->
            ListItemCard(
                text = recommendation.title,
                onItemClick = { onItemClick(recommendation) }
            )
        }
    }
}

/**
 * Card chung cho các mục trong danh sách, không đổi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemCard(
    text: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
 * Màn hình chi tiết, đã cải tiến để có thể cuộn nếu nội dung dài.
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
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = recommendation.imageResId),
            contentDescription = recommendation.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = recommendation.title, style = MaterialTheme.typography.headlineLarge)
        Text(text = recommendation.description, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Địa chỉ: ${recommendation.address}", style = MaterialTheme.typography.bodyMedium)
    }
}
