package com.example.reply.ui.utils
// Enum này định nghĩa 3 loại thanh điều hướng mà chúng ta sẽ sử dụng.
enum class ReplyNavigationType {
    BOTTOM_NAVIGATION,      // Thanh điều hướng dưới đáy
    NAVIGATION_RAIL,        // Thanh điều hướng cạnh
    PERMANENT_NAVIGATION_DRAWER // Ngăn điều hướng cố định bên trái
}

enum class ReplyContentType {
    LIST_ONLY,          // Chỉ hiển thị danh sách
    LIST_AND_DETAIL     // Hiển thị cả danh sách và chi tiết
}