package com.example.juicetracker.ui.bottomsheet

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.juicetracker.R
import com.example.juicetracker.data.JuiceColor

// Lắng nghe sự kiện chọn màu trong Spinner
class SpinnerAdapter(val onColorChange: (Int) -> Unit) : AdapterView.OnItemSelectedListener {

    // Khi chọn item
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onColorChange(position)
    }

    // Khi không chọn gì → mặc định màu đầu tiên
    override fun onNothingSelected(parent: AdapterView<*>?) {
        onColorChange(0)
    }
}

@Composable
fun ColorSpinnerRow(
    colorSpinnerPosition: Int,
    onColorChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Lấy danh sách tên màu từ enum
    val juiceColorArray = JuiceColor.values().map { stringResource(it.label) }

    // Hàng hiển thị label và Spinner chọn màu
    InputRow(inputLabel = stringResource(R.string.color), modifier = modifier) {

        // Dùng AndroidView để hiển thị Spinner trong Compose
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // Tạo Spinner và gán dữ liệu
                Spinner(context).apply {
                    adapter = ArrayAdapter(
                        context,
                        android.R.layout.simple_spinner_dropdown_item,
                        juiceColorArray
                    )
                }
            },
            update = { spinner ->
                // Cập nhật vị trí được chọn và gán listener
                spinner.setSelection(colorSpinnerPosition)
                spinner.onItemSelectedListener = SpinnerAdapter(onColorChange)
            }
        )
    }
}
