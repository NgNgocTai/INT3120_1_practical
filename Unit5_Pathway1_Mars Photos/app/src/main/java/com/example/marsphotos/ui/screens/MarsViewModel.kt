package com.example.marsphotos.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MarsViewModel : ViewModel() {
    var marsUiState: String by mutableStateOf("")
        private set

    init {
        getMarsPhotos() // gọi ngay khi ViewModel khởi tạo
    }

    private fun getMarsPhotos() {
        // chạy trong coroutine → không chặn luồng UI
        viewModelScope.launch {
            val listResult = MarsApi.retrofitService.getPhotos()
            marsUiState = listResult  // cập nhật state → UI tự render lại
        }
    }
}
