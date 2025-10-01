package com.example.cupcake

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

// Định nghĩa các màn hình (enum để quản lý route dễ dàng)
enum class CupcakeScreen {
    Start,
    Flavor,
    Pickup,
    Summary
}

/**
 * Thanh AppBar trên cùng (có tiêu đề và nút back nếu quay lại được)
 */
@Composable
fun CupcakeAppBar(
    canNavigateBack: Boolean,       // có cho phép back không
    navigateUp: () -> Unit,         // hành động khi nhấn nút back
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },  // tiêu đề = tên app
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {  // nếu có thể back thì hiển thị nút mũi tên
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Hàm gốc của app Cupcake, chứa NavHost quản lý navigation giữa các màn hình.
 */
@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = navController.previousBackStackEntry != null, // kiểm tra có thể back không
                navigateUp = { navController.navigateUp() } // hành động back
            )
        }
    ) { innerPadding ->
        // lấy state từ ViewModel
        val uiState by viewModel.uiState.collectAsState()

        // NavHost quản lý các màn hình
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name, // màn hình mặc định
            modifier = Modifier.padding(innerPadding)
        ) {
            // Start screen
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
//                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Flavor.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            // Flavor screen
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
//                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            // Pickup screen
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
//                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            // Summary screen
            composable(route = CupcakeScreen.Summary.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
//                    onCancelButtonClicked = {
//                        viewModel.resetOrder()
//                        navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
//                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}
