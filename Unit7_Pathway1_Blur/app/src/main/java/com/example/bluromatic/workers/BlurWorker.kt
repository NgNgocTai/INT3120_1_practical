package com.example.bluromatic.workers

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import androidx.work.workDataOf

private const val TAG = "BlurWorker"

class BlurWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        val blurLevel = inputData.getInt(KEY_BLUR_LEVEL, 1)
        // Hiển thị thông báo: đang làm mờ ảnh
        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )

        return withContext(Dispatchers.IO) { // Chạy trong luồng I/O (thích hợp cho xử lý ảnh & ghi file)
            require(!resourceUri.isNullOrBlank()) {
                val errorMessage =
                    applicationContext.resources.getString(R.string.invalid_input_uri)
                Log.e(TAG, errorMessage)
                errorMessage
            }
            //Khởi tạo đối tượng contentResolver để làm việc với uri
            val resolver = applicationContext.contentResolver
            // Giả lập quá trình xử lý chậm (delay không chặn thread)
            delay(DELAY_TIME_MILLIS)

            return@withContext try {
                // Đọc ảnh từ Uri
                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
                )

                // Làm mờ ảnh (hàm helper bên ngoài)
                val output = blurBitmap(picture, blurLevel)

                // Ghi ảnh đã làm mờ ra file tạm và nhận URI của file đó
                val outputUri = writeBitmapToFile(applicationContext, output)

                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                // Trả về kết quả thành công
                Result.success(outputData)

            } catch (throwable: Throwable) {
                // Nếu có lỗi thì ghi log và báo thất bại
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )
                Result.failure()
            }
        }
    }
}
