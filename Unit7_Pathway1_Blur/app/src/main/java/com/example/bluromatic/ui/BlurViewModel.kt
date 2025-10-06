package com.example.bluromatic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.example.bluromatic.BluromaticApplication
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.data.BlurAmountData
import com.example.bluromatic.data.BluromaticRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel điều khiển WorkManager và cập nhật trạng thái giao diện.
 */
class BlurViewModel(
    private val bluromaticRepository: BluromaticRepository
) : ViewModel() {

    internal val blurAmount = BlurAmountData.blurAmount

    /**
     * Dòng dữ liệu trạng thái giao diện (UI State)
     * Được xây dựng từ trạng thái của công việc (WorkInfo) trong repository.
     */
    val blurUiState: StateFlow<BlurUiState> = bluromaticRepository.outputWorkInfo
        .map { info ->
            val outputImageUri = info!!.outputData.getString(KEY_IMAGE_URI)
            when {
                info.state.isFinished && !outputImageUri.isNullOrEmpty() -> {
                    // Khi công việc hoàn tất và có ảnh đầu ra
                    BlurUiState.Complete(outputUri = outputImageUri)
                }
                info.state == WorkInfo.State.CANCELLED -> {
                    BlurUiState.Default
                }
                info.state == WorkInfo.State.RUNNING -> {
                    BlurUiState.Loading
                }
                else -> {
                    BlurUiState.Default
                }
            }
        }
        // Biến Flow "nguội" thành StateFlow "nóng" có thể quan sát được trong UI
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BlurUiState.Default
        )

    /** Gọi repository để bắt đầu làm mờ ảnh */
    fun applyBlur(blurLevel: Int) {
        bluromaticRepository.applyBlur(blurLevel)
    }

    /** Gọi repository để hủy công việc */
    fun cancelWork() {
        bluromaticRepository.cancelWork()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val bluromaticRepository =
                    (this[APPLICATION_KEY] as BluromaticApplication)
                        .container.bluromaticRepository
                BlurViewModel(bluromaticRepository)
            }
        }
    }
}

/** Các trạng thái có thể có của giao diện */
sealed interface BlurUiState {
    object Default : BlurUiState
    object Loading : BlurUiState
    data class Complete(val outputUri: String) : BlurUiState
}
