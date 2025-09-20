package com.example.cardbusiness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardbusiness.ui.theme.CardBusinessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardBusinessTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(
                        name = stringResource(R.string.name),
                        des = stringResource(R.string.des),
                        phone = stringResource(R.string.phone),
                        share = stringResource(R.string.share),
                        mail = stringResource(R.string.mail),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    name: String,
    des: String,
    phone: String,
    share: String,
    mail: String,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.android_logo)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFD0E8FF))
    ) {

        // Ảnh, tên, chức danh ở giữa màn hình
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = "Android Logo",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF073042))
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 32.sp,
                color = Color.Black
            )
            Text(
                text = des,
                fontSize = 16.sp,
                color = Color(0xFF00695C)
            )
        }

        // Thông tin liên lạc ở cuối màn hình
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ContactRow(
                icon = Icons.Filled.Phone,
                text = phone,
                tint = Color(0xFF00695C)
            )
            ContactRow(
                icon = Icons.Filled.Share,
                text = share,
                tint = Color(0xFF00695C)
            )
            ContactRow(
                icon = Icons.Filled.Email,
                text = mail,
                tint = Color(0xFF00695C)
            )
        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, text: String, tint: Color, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(horizontal = 40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardPreview() {
    CardBusinessTheme {
        BusinessCard(
            name = "Jennifer Doe",
            des = "Android Developer Extraordinaire",
            phone = "+84 123 456 789",
            share = "@androiddev",
            mail = "jen.doe@android.com"
        )
    }
}
