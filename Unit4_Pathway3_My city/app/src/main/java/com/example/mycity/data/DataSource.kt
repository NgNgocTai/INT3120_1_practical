package com.example.mycity.data

import com.example.mycity.R

object DataSource {
    val categories = listOf(
        Category(
            id = 1,
            name = "Quán Cà Phê",
            recommendations = listOf(
                Recommendation(1, "Giảng Cafe", "Nổi tiếng với món Cà phê Trứng trứ danh.", "39 Nguyễn Hữu Huân, Hoàn Kiếm", R.drawable.giang_cafe),
                Recommendation(2, "Cộng Cà Phê", "Chuỗi cà phê mang phong cách retro thời bao cấp.", "Nhiều chi nhánh", R.drawable.cong_cafe),
                Recommendation(3, "The Note Coffee", "Quán cà phê độc đáo với hàng ngàn mẩu giấy ghi chú.", "64 Lương Văn Can, Hoàn Kiếm", R.drawable.note_cafe)
            )
        ),
        Category(
            id = 2,
            name = "Địa điểm Tham quan",
            recommendations = listOf(
                Recommendation(4, "Hồ Gươm", "Trái tim của thủ đô Hà Nội, gắn liền với truyền thuyết trả gươm báu.", "Hoàn Kiếm", R.drawable.ho_guom),
                Recommendation(5, "Văn Miếu - Quốc Tử Giám", "Trường đại học đầu tiên của Việt Nam, nơi thờ Khổng Tử.", "58 Quốc Tử Giám, Đống Đa", R.drawable.van_mieu)
            )
        ),
        Category(
            id = 3,
            name = "Ẩm thực",
            recommendations = listOf(
                Recommendation(6, "Bún chả Hương Liên", "Quán bún chả nổi tiếng sau chuyến thăm của tổng thống Obama.", "24 Lê Văn Hưu, Hai Bà Trưng", R.drawable.bun_cha),
                Recommendation(7, "Phở Thìn", "Một trong những hàng phở bò lâu đời và đặc trưng nhất Hà Nội.", "13 Lò Đúc, Hai Bà Trưng", R.drawable.pho_thin)
            )
        )
    )
}