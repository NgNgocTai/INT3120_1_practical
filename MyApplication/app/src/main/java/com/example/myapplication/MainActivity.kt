package com.example.myapplication // Thay đổi tên package cho đúng với dự án của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme // Thay đổi cho đúng với theme của bạn

// ---- ĐỊNH NGHĨA CÁC "ROUTE" (ĐƯỜNG DẪN) CHO CÁC MÀN HÌNH ----
object AppRoutes {
    const val HOME = "home_screen"
    const val HELLO_WORLD = "hello_world_screen"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyApp() // Gọi hàm Composable chính của ứng dụng
            }
        }
    }
}

@Composable
fun MyApp() {
    // Tạo NavController để quản lý việc chuyển trang
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // Thiết lập NavHost để khai báo các màn hình
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME, // Màn hình bắt đầu khi mở app
            modifier = Modifier.padding(innerPadding)
        ) {
            // Khai báo màn hình chính (có logo)
            composable(route = AppRoutes.HOME) {
                HomeScreen(
                    onImageClick = {
                        navController.navigate(AppRoutes.HELLO_WORLD)
                    }
                )
            }

            // Khai báo màn hình "Hello World"
            composable(route = AppRoutes.HELLO_WORLD) {
                HelloWorldScreen()
            }
        }
    }
}

// ---- MÀN HÌNH CHÍNH (CÓ LOGO) ----
@Composable
fun HomeScreen(onImageClick: () -> Unit) {
    // Đảm bảo bạn có file `logo.png` trong thư mục `res/drawable`
    val image = painterResource(id = R.drawable.logo)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = "Logo",
            modifier = Modifier
                .size(250.dp) // Chỉnh kích thước logo
                .clickable {
                    onImageClick()
                }
        )
    }
}

// ---- MÀN HÌNH THỨ HAI (ĐÃ THÊM STYLE) ----
@Composable
fun HelloWorldScreen() {
    // Tách các dòng cam kết ra thành một danh sách để dễ xử lý
    val commitments = listOf(
        "Em là Nguyễn Ngọc Tài",
        "Quyết tâm A+ môn mobile",
        "Em xin cam kết sẽ không gian lận và tiếp tay cho gian lận, nếu vi phạm em sẽ trượt môn"
    )

    // Column chính để căn giữa mọi thứ trên màn hình
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), // Thêm padding ngang để nội dung không bị sát viền
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dùng một Column con để chứa các dòng text có chấm tròn
        // và căn lề trái (Start) cho chúng thẳng hàng
        Column(horizontalAlignment = Alignment.Start) {
            // Dùng vòng lặp để tạo ra mỗi dòng cam kết
            commitments.forEach { text ->
                // Mỗi dòng là một Row để chứa (chấm tròn + text)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp) // Khoảng cách giữa các dòng
                ) {
                    // Chấm tròn (•)
                    Text(
                        text = "•",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary // Lấy màu chính từ Theme
                    )

                    Spacer(modifier = Modifier.width(12.dp)) // Khoảng cách giữa chấm và chữ

                    // Nội dung text
                    Text(
                        text = text,
                        fontSize = 18.sp, // Cỡ chữ
                        color = Color(0xFF333333), // Màu chữ xám đậm
                        textAlign = TextAlign.Start // Căn lề trái cho nội dung chữ
                    )
                }
            }
        }
    }
}


// ---- PREVIEW ----
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen(onImageClick = {}) // Truyền vào một hàm rỗng cho preview
    }
}

@Preview(showBackground = true)
@Composable
fun HelloWorldScreenPreview() {
    MyApplicationTheme {
        HelloWorldScreen() // Xem trước màn hình thứ hai với giao diện mới
    }
}