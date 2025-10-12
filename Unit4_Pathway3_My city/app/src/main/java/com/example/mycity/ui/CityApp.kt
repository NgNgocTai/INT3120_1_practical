package com.example.mycity.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.data.Recommendation

// Enum để định danh các màn hình, không đổi
enum class CityScreen {
    Categories,
    Recommendations,
    Detail
}

/**
 * Composable cho thanh tiêu đề (TopAppBar) có thể tái sử dụng.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Quay lại"
                    )
                }
            }
        }
    )
}

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val viewModel: CityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Lấy thông tin về màn hình hiện tại từ NavController
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreenName = backStackEntry?.destination?.route ?: CityScreen.Categories.name

    // Quyết định tiêu đề dựa trên màn hình hiện tại
    val screenTitle = when (currentScreenName) {
        CityScreen.Categories.name -> "Khám Phá Hà Nội"
        CityScreen.Recommendations.name -> uiState.currentCategory?.name ?: ""
        CityScreen.Detail.name -> uiState.currentRecommendation?.title ?: ""
        else -> "Khám Phá Hà Nội"
    }

    // Quyết định layout dựa trên kích thước màn hình
    val useListAndDetailLayout = windowSize == WindowWidthSizeClass.Expanded || windowSize == WindowWidthSizeClass.Medium

    if (useListAndDetailLayout) {
        // Layout cho máy tính bảng
        Scaffold(
            topBar = {
                CityTopAppBar(
                    title = "Khám Phá Hà Nội",
                    canNavigateBack = false,
                    onNavigateUp = { /* Không làm gì */ }
                )
            }
        ) { innerPadding ->
            CityListAndDetailLayout(
                uiState = uiState,
                onCategoryClick = { viewModel.updateCurrentCategory(it) },
                onRecommendationClick = { viewModel.updateCurrentRecommendation(it) },
                modifier = Modifier.padding(innerPadding)
            )
        }
    } else {
        // Layout cho điện thoại với Scaffold và TopAppBar
        Scaffold(
            topBar = {
                CityTopAppBar(
                    title = screenTitle,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        ) { innerPadding ->
            // Đặt NavHost vào trong Scaffold
            NavHost(
                navController = navController,
                startDestination = CityScreen.Categories.name,
                modifier = Modifier.padding(innerPadding)
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
    }
}


/**
 * Layout cho màn hình lớn (máy tính bảng), không thay đổi nhiều
 */
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
