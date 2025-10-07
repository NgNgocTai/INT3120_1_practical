package com.example.mycity.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycity.data.Category
import com.example.mycity.data.Recommendation

enum class CityScreen {
    Categories,
    Recommendations,
    Detail
}

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass
) {
    val viewModel: CityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val useListAndDetailLayout = windowSize == WindowWidthSizeClass.Expanded || windowSize == WindowWidthSizeClass.Medium

    if (useListAndDetailLayout) {
        CityListAndDetailLayout(
            uiState = uiState,
            onCategoryClick = { viewModel.updateCurrentCategory(it) },
            onRecommendationClick = { viewModel.updateCurrentRecommendation(it) }
        )
    } else {
        CityAppNavigation(uiState = uiState, viewModel = viewModel)
    }
}

@Composable
fun CityAppNavigation(
    uiState: CityUiState,
    viewModel: CityViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CityScreen.Categories.name
    ) {
        composable(route = CityScreen.Categories.name) {
            CategoryListScreen(
                categories = uiState.categories,
                onItemClick = { category ->
                    viewModel.updateCurrentCategory(category)
                    navController.navigate(CityScreen.Recommendations.name)
                }
            )
        }

        composable(route = CityScreen.Recommendations.name) {
            val recommendations = uiState.currentCategory?.recommendations ?: emptyList()
            RecommendationsListScreen(
                recommendations = recommendations,
                onItemClick = { recommendation ->
                    viewModel.updateCurrentRecommendation(recommendation)
                    navController.navigate(CityScreen.Detail.name)
                }
            )
        }

        composable(route = CityScreen.Detail.name) {
            uiState.currentRecommendation?.let { recommendation ->
                RecommendationDetailScreen(recommendation = recommendation)
            }
        }
    }
}

@Composable
fun CityListAndDetailLayout(
    uiState: CityUiState,
    onCategoryClick: (Category) -> Unit,
    onRecommendationClick: (Recommendation) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        CategoryListScreen(
            categories = uiState.categories,
            onItemClick = onCategoryClick,
            modifier = Modifier.weight(1f)
        )
        val recommendations = uiState.currentCategory?.recommendations ?: emptyList()
        RecommendationsListScreen(
            recommendations = recommendations,
            onItemClick = onRecommendationClick,
            modifier = Modifier.weight(1f)
        )
        uiState.currentRecommendation?.let { recommendation ->
            RecommendationDetailScreen(
                recommendation = recommendation,
                modifier = Modifier.weight(1.5f)
            )
        }
    }
}