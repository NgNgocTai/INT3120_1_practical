package com.example.superheroes

import SuperheroesTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                    SuperheroApp()
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperheroesTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name), // tên app
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}


@Composable
fun SuperheroApp() {
        Scaffold(
            topBar = { SuperheroesTopAppBar() } // <-- top app bar chỉ có text
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(HeroesRepository.heroes) { hero ->
                    SuperheroItem(
                        hero = hero,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
@Composable
fun SuperheroItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(72.dp)
                .padding(16.dp)
        ) {
            // Column chứa text, weight 1 để chiếm toàn bộ không gian còn lại
            SuperheroInfo(
                heroName = hero.nameRes,
                heroDes = hero.descriptionRes,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(16.dp))

            // Image hero
            SuperheroIcon(hero.imageRes)
        }
    }
}

@Composable
fun SuperheroInfo(
    @StringRes heroName: Int,
    @StringRes heroDes: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(heroName),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(heroDes),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SuperheroIcon(
    @DrawableRes superheroIcon: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .then(modifier) // kết hợp modifier từ ngoài nếu có
    ) {
        Image(
            painter = painterResource(superheroIcon),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuperheroAppPreview() {
    SuperheroesTheme(darkTheme = true) {
        SuperheroApp()
    }
}
