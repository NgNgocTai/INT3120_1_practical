package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.model.ArtRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtImage(@DrawableRes imageResourceId:Int, @StringRes title:Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(imageResourceId),
        contentDescription = stringResource(title),
        modifier = modifier
    )
}

@Composable
fun ArtInfo(@StringRes title:Int, @StringRes artist:Int, year:Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(title)
        )
        Text(
            text = stringResource(artist) + "($year)"
        )
    }
}


@Composable
fun ArtButton(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        // Previous Button
        Box(
            modifier = Modifier
                .size(80.dp, 50.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = onPreviousClick) {
                Text(text = "Previous", fontSize = 16.sp)
            }
        }

        // Next Button
        Box(
            modifier = Modifier
                .size(80.dp, 50.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = onNextClick) {
                Text(text = "Next", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun Artspace(modifier: Modifier = Modifier) {
    val art = ArtRepository.arts[0];
    Column(modifier = modifier) {
        ArtImage(art.imageResourceId,art.title)
        ArtInfo(art.title, art.artist, art.year)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        Artspace()
    }
}