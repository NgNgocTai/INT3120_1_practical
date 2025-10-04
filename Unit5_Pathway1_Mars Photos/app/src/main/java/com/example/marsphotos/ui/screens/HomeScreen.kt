package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R
import com.example.marsphotos.ui.theme.MarsPhotosTheme

@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        )

        is MarsUiState.Success -> ResultScreen(
            photos = marsUiState.photos,
            modifier = modifier
                .fillMaxWidth()
                .padding(contentPadding)
        )

        is MarsUiState.Error -> ErrorScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}


@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Text(text = photos)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    MarsPhotosTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}
