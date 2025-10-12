package com.example.juicetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juicetracker.data.Juice
import com.example.juicetracker.data.JuiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// ViewModel quản lý danh sách nước ép và xử lý xóa dữ liệu từ JuiceRepository
class TrackerViewModel(private val juiceRepository: JuiceRepository) : ViewModel() {

    val juicesStream: Flow<List<Juice>> = juiceRepository.juicesStream

    fun deleteJuice(juice: Juice) = viewModelScope.launch {
        juiceRepository.deleteJuice(juice)
    }
}
