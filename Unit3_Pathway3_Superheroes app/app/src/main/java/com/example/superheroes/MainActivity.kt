package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository
import SuperheroesTheme

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
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}

@Composable
fun SuperheroApp() {
    var isTwoTeams by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { SuperheroesTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { isTwoTeams = !isTwoTeams }) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = stringResource(R.string.split_teams)
                )
            }
        }
    ) { innerPadding ->
        if (isTwoTeams) {
            SuperheroTwoTeamsLayout(
                heroes = HeroesRepository.heroes,
                contentPadding = innerPadding
            )
        } else {
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
}

@Composable
fun SuperheroTwoTeamsLayout(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    val team1 = heroes.filterIndexed { index, _ -> index % 2 == 0 }
    val team2 = heroes.filterIndexed { index, _ -> index % 2 != 0 }

    if (isLandscape) {
        // Landscape: 2 cột
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Team 1",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn {
                    items(team1) { hero ->
                        SuperheroItem(hero = hero, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Team 2",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn {
                    items(team2) { hero ->
                        SuperheroItem(hero = hero, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    } else {
        // Portrait: 2 hàng
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(
                    text = "Team 1",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn {
                    items(team1) { hero ->
                        SuperheroItem(hero = hero, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
            Column {
                Text(
                    text = "Team 2",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyColumn {
                    items(team2) { hero ->
                        SuperheroItem(hero = hero, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
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
                .sizeIn(minHeight = 72.dp)
                .padding(16.dp)
        ) {
            SuperheroInfo(
                heroName = hero.nameRes,
                heroDes = hero.descriptionRes,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
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
            .then(modifier)
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
    SuperheroesTheme(darkTheme = false) {
        SuperheroApp()
    }
}
