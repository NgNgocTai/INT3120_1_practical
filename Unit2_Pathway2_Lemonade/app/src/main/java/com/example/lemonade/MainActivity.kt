package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonade.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lemonade", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            when (currentStep) {
                1 -> LemonStep(
                    textRes = R.string.lemon_select,
                    imageRes = R.drawable.lemon_tree,
                    contentDescRes = R.string.lemon_tree_content_description,
                    onClick = {
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }
                )
                2 -> LemonStep(
                    textRes = R.string.lemon_squeeze,
                    imageRes = R.drawable.lemon_squeeze,
                    contentDescRes = R.string.lemon_content_description,
                    onClick = {
                        squeezeCount--
                        if (squeezeCount == 0) currentStep = 3
                    }
                )
                3 -> LemonStep(
                    textRes = R.string.lemon_drink,
                    imageRes = R.drawable.lemon_drink,
                    contentDescRes = R.string.lemonade_content_description,
                    onClick = { currentStep = 4 }
                )
                4 -> LemonStep(
                    textRes = R.string.lemon_empty_glass,
                    imageRes = R.drawable.lemon_restart,
                    contentDescRes = R.string.empty_glass_content_description,
                    onClick = { currentStep = 1 }
                )
            }
        }
    }
}

@Composable
fun LemonStep(
    textRes: Int,
    imageRes: Int,
    contentDescRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = stringResource(contentDescRes),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(dimensionResource(R.dimen.button_image_height))
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
            Text(text = stringResource(textRes), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonPreview() {
    AppTheme { LemonadeApp() }
}
