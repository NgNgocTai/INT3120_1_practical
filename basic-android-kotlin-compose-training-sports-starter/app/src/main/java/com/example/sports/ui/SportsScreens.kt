package com.example.sports.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sports.R
import com.example.sports.data.LocalSportsDataProvider
import com.example.sports.model.Sport
import com.example.sports.ui.theme.SportsTheme
import com.example.sports.utils.SportsContentType

/**
 * Main container composable của app.
 * Hiển thị List, Detail, hoặc ListAndDetail dựa vào windowSize và uiState.
 */
@Composable
fun SportsApp(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val viewModel: SportsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // Xác định kiểu hiển thị
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> SportsContentType.ListOnly
        WindowWidthSizeClass.Expanded -> SportsContentType.ListAndDetail
        else -> SportsContentType.ListOnly
    }

    Scaffold(
        topBar = {
            SportsAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackButtonClick = { viewModel.navigateToListPage() },
                windowSize = windowSize
            )
        }
    ) { innerPadding ->
        when (contentType) {
            SportsContentType.ListAndDetail -> {
                SportsListAndDetail(
                    sports = uiState.sportsList,
                    selectedSport = uiState.currentSport,
                    onClick = { viewModel.updateCurrentSport(it) },
                    onBackPressed = onBackPressed,
                    contentPadding = innerPadding
                )
            }
            SportsContentType.ListOnly -> {
                if (uiState.isShowingListPage) {
                    SportsList(
                        sports = uiState.sportsList,
                        onClick = {
                            viewModel.updateCurrentSport(it)
                            viewModel.navigateToDetailPage()
                        },
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                        contentPadding = innerPadding
                    )
                } else {
                    SportsDetail(
                        selectedSport = uiState.currentSport,
                        contentPadding = innerPadding,
                        onBackPressed = { viewModel.navigateToListPage() }
                    )
                }
            }
        }
    }
}

/**
 * TopAppBar hiển thị tiêu đề và nút Back nếu đang ở màn hình Detail.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val isDetail = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage

    TopAppBar(
        title = {
            Text(
                text = if (isDetail) stringResource(R.string.detail_fragment_label)
                else stringResource(R.string.list_fragment_label),
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = if (isDetail) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                }
            }
        } else { { Box {} } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier
    )
}

/**
 * Hiển thị danh sách các môn thể thao.
 */
@Composable
private fun SportsList(
    sports: List<Sport>,
    onClick: (Sport) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(sports, key = { it.id }) { sport ->
            SportsListItem(sport = sport, onItemClick = onClick)
        }
    }
}

/**
 * Item hiển thị thông tin cơ bản của môn thể thao trong danh sách.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SportsListItem(
    sport: Sport,
    onItemClick: (Sport) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(sport) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            SportsListImageItem(
                sport = sport,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(sport.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(sport.subtitleResourceId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f))
                Row {
                    Text(
                        text = pluralStringResource(
                            R.plurals.player_count_caption,
                            sport.playerCount,
                            sport.playerCount
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                    if (sport.olympic) {
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

/**
 * Hình ảnh đại diện môn thể thao trong item.
 */
@Composable
private fun SportsListImageItem(sport: Sport, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(sport.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}

/**
 * Chi tiết môn thể thao khi chọn vào item.
 */
@Composable
private fun SportsDetail(
    selectedSport: Sport,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler { onBackPressed() }

    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier.verticalScroll(scrollState).padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier.padding(
                start = contentPadding.calculateStartPadding(layoutDirection),
                end = contentPadding.calculateEndPadding(layoutDirection),
                bottom = contentPadding.calculateTopPadding()
            )
        ) {
            Box {
                Image(
                    painter = painterResource(selectedSport.sportsImageBanner),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedSport.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    Row(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
                        Text(
                            text = pluralStringResource(
                                R.plurals.player_count_caption,
                                selectedSport.playerCount,
                                selectedSport.playerCount
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(R.string.olympic_caption),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
            }
            Text(
                text = stringResource(selectedSport.sportDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}

/**
 * Hiển thị List và Detail cạnh nhau cho màn hình lớn.
 */
@Composable
private fun SportsListAndDetail(
    sports: List<Sport>,
    selectedSport: Sport,
    onClick: (Sport) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(modifier = modifier.fillMaxWidth()) {
        SportsList(
            sports = sports,
            onClick = onClick,
            modifier = Modifier.weight(2f).padding(horizontal = dimensionResource(R.dimen.padding_medium)),
            contentPadding = PaddingValues(top = contentPadding.calculateTopPadding())
        )
        SportsDetail(
            selectedSport = selectedSport,
            onBackPressed = onBackPressed,
            modifier = Modifier.weight(3f),
            contentPadding = PaddingValues(top = contentPadding.calculateTopPadding())
        )
    }
}

/**
 * Preview các composable
 */
@Preview(showBackground = true)
@Composable
fun SportsListItemPreview() {
    SportsTheme {
        SportsListItem(
            sport = LocalSportsDataProvider.defaultSport,
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SportsListPreview() {
    SportsTheme {
        Surface {
            SportsList(
                sports = LocalSportsDataProvider.getSportsData(),
                onClick = {},
            )
        }
    }
}
