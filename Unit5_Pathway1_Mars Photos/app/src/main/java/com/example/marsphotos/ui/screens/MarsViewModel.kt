package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

// 1️⃣ Định nghĩa sealed interface cho 3 trạng thái
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {

    // 2️⃣ Biến trạng thái UI — mặc định là Loading
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    // 3️⃣ Gọi API và cập nhật UI state
    private fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try {
                val listResult = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success(listResult)
            } catch (e: IOException) {
                MarsUiState.Error
            }
        }
    }
}
