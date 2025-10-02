package com.example.a30days

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30days.data.TipsRepository
import com.example.a30days.model.Tip
import com.example.a30days.ui.theme.ThirtyDaysOfWellnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyDaysOfWellnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThirtyDaysApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysApp() {
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = scrollState,
            contentPadding = paddingValues,
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(TipsRepository.tips) { tip ->
                TipCard(tip = tip)
            }
        }
    }
}

@Composable
fun TipCard(tip: Tip, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Text(
                text = stringResource(R.string.day_prefix, tip.day),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(tip.titleRes),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(tip.imageRes),
                contentDescription = stringResource(tip.titleRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            if (expanded) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(tip.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirtyDaysAppPreview() {
    ThirtyDaysOfWellnessTheme {
        ThirtyDaysApp()
    }
}