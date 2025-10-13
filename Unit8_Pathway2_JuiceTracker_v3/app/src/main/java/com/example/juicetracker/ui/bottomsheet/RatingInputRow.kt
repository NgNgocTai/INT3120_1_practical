package com.example.juicetracker.ui.bottomsheet

import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.juicetracker.R

@Composable
fun RatingInputRow(rating: Int, onRatingChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    InputRow(inputLabel = stringResource(R.string.rating), modifier = modifier) {
        // Lại sử dụng AndroidView, người bạn thân của chúng ta
        AndroidView(
            // `factory`: Tạo RatingBar một lần duy nhất
            factory = { context ->
                RatingBar(context).apply {
                    // Thiết lập để chỉ có thể chọn sao chẵn (1, 2, 3...)
                    stepSize = 1f
                }
            },
            // `update`: Cập nhật mỗi khi trạng thái thay đổi
            update = { ratingBar ->
                // Cập nhật số sao đang hiển thị
                ratingBar.rating = rating.toFloat()
                // Lắng nghe sự kiện khi người dùng thay đổi số sao
                ratingBar.setOnRatingBarChangeListener { _, _, _ ->
                    onRatingChange(ratingBar.rating.toInt())
                }
            }
        )
    }
}