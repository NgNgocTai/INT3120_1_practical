package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.model.ArtRepository
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtspaceApp()
                }
            }
        }
    }
}

/**
 * Composable chính chứa toàn bộ logic và giao diện của ứng dụng.
 */
@Composable
fun ArtspaceApp() {
    // Quản lý state của artwork hiện tại
    var currentArtIndex by remember { mutableStateOf(0) }
    val artList = ArtRepository.arts
    val currentArt = artList[currentArtIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Ảnh artwork
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            ArtImage(
                imageResourceId = currentArt.imageResourceId,
                title = currentArt.title
            )
        }

        // Thông tin artwork
        ArtInfo(
            title = currentArt.title,
            artist = currentArt.artist,
            year = currentArt.year
        )

        // Nút Previous / Next
        ArtButtons(
            onPreviousClick = {
                currentArtIndex =
                    if (currentArtIndex == 0) artList.size - 1 else currentArtIndex - 1
            },
            onNextClick = {
                currentArtIndex =
                    if (currentArtIndex == artList.size - 1) 0 else currentArtIndex + 1
            }
        )
    }
}

/**
 * Composable hiển thị ảnh artwork trong Card.
 */
@Composable
fun ArtImage(
    @DrawableRes imageResourceId: Int,
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = stringResource(title),
            modifier = Modifier
                .padding(24.dp)
                .size(400.dp)
        )
    }
}

/**
 * Composable hiển thị thông tin tác phẩm.
 */
@Composable
fun ArtInfo(
    @StringRes title: Int,
    @StringRes artist: Int,
    year: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${stringResource(artist)} ($year)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Composable hiển thị các nút Previous / Next.
 */
@Composable
fun ArtButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtspaceAppPreview() {
    ArtSpaceTheme {
        ArtspaceApp()
    }
}
