package com.example.marsphotos.ui.screens

import MarsPhotosRepository
import NetworkMarsPhotosRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

// 1️⃣ Trạng thái UI
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotosRepository: MarsPhotosRepository = NetworkMarsPhotosRepository()
) : ViewModel() {

    // 2️⃣ Trạng thái UI — mặc định là Loading
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    // 3️⃣ Lấy dữ liệu qua repository
    private fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try {
                MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
            } catch (e: Exception) {
                e.printStackTrace()
                MarsUiState.Error
            }
        }
    }
}
