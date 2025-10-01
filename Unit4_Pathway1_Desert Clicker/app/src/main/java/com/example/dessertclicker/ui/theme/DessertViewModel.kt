package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Lớp data class chứa trạng thái giao diện.
 */
data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertPrice: Int = Datasource.dessertList.first().price,
    val currentDessertImageId: Int = Datasource.dessertList.first().imageId,
)

/**
 * ViewModel cho màn hình Dessert Clicker.
 */
class DessertViewModel : ViewModel() {

    // StateFlow riêng tư chứa trạng thái hiện tại.
    private val _uiState = MutableStateFlow(DessertUiState())

    // StateFlow công khai chỉ đọc cho giao diện.
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    /**
     * Cập nhật trạng thái khi bánh được click.
     */
    fun onDessertClicked() {
        _uiState.update { currentState ->
            val newRevenue = currentState.revenue + currentState.currentDessertPrice
            val newDessertsSold = currentState.dessertsSold + 1
            val dessertToShow = determineDessertToShow(newDessertsSold)

            currentState.copy(
                revenue = newRevenue,
                dessertsSold = newDessertsSold,
                currentDessertPrice = dessertToShow.price,
                currentDessertImageId = dessertToShow.imageId
            )
        }
    }

    /**
     * Chọn bánh để hiển thị dựa trên số lượng đã bán.
     */
    private fun determineDessertToShow(
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = Datasource.dessertList.first()
        for (dessert in Datasource.dessertList) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                break
            }
        }
        return dessertToShow
    }
}

