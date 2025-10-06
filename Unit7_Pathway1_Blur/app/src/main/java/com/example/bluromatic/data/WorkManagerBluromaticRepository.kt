package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.getImageUri
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    // URI của hình ảnh cần xử lý
    private var imageUri: Uri = context.getImageUri()

    // Quản lý các tác vụ nền
    private val workManager = WorkManager.getInstance(context)

    // Lưu thông tin về tiến trình công việc
    override val outputWorkInfo: Flow<WorkInfo?> = MutableStateFlow(null)

    /**
     * Tạo chuỗi công việc để:
     * 1. Dọn file tạm
     * 2. Làm mờ ảnh
     * 3. Lưu ảnh mờ ra file vĩnh viễn
     */
    override fun applyBlur(blurLevel: Int) {
        // Bước 1: Dọn file tạm
        var continuation = workManager.beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))

        // Bước 2: Làm mờ ảnh
        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
        continuation = continuation.then(blurBuilder.build())

        // Bước 3: Lưu ảnh mờ ra bộ nhớ
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>().build()
        continuation = continuation.then(save)

        // Bắt đầu chạy chuỗi công việc
        continuation.enqueue()
    }

    /** Hủy các công việc đang chạy (chưa triển khai) */
    override fun cancelWork() {}

    /**
     * Tạo dữ liệu đầu vào cho Worker:
     * - Gồm URI ảnh và mức độ làm mờ
     */
    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString())
            .putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
