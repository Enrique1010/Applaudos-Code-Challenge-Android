package com.erapps.moviesinfoapp.ui.screens.details

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.ShortSeason
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.TvShowDetails
import com.erapps.moviesinfoapp.ui.shared.BackButtonBar
import com.erapps.moviesinfoapp.ui.shared.DetailsPageWithState
import com.erapps.moviesinfoapp.ui.shared.RatingBar
import com.erapps.moviesinfoapp.ui.shared.UiState
import com.erapps.moviesinfoapp.ui.theme.dimen
import com.erapps.moviesinfoapp.utils.getImageByPath

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(uiState = uiState, onBackPressed = onBackPressed)
}

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: UiState?,
    onBackPressed: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex >= 1
        }
    }

    DetailsPageWithState<TvShowDetails>(
        uiState = uiState,
    ) { tvShowDetails ->

        Scaffold(
            modifier = modifier,
            topBar = {
                AnimatedVisibility(
                    visible = firstItemVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    AppBar(
                        titleText = tvShowDetails.name,
                        onBackPressed = onBackPressed
                    )
                }
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                state = lazyListState
            ) {
                item {
                    HeaderSection(
                        modifier = Modifier,
                        imageUrl = tvShowDetails.backdrop_path,
                        name = tvShowDetails.name,
                        average = (tvShowDetails.vote_average / 2)
                    )
                }
                item {
                    SummarySection(overView = tvShowDetails.overview)
                }
                item {
                    SeasonsSection(seasons = tvShowDetails.seasons)
                }
            }
            if (!firstItemVisible) {
                BackButtonBar(onBackPressed = onBackPressed)
            }
        }
    }
}

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    titleText: String,
    onBackPressed: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = titleText,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    average: Double
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {

        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            model = ImageRequest.Builder(context)
                .data(imageUrl.getImageByPath())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = name,
            alignment = Alignment.Center,
            loading = { LinearProgressIndicator() },
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier.padding(MaterialTheme.dimen.medium),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            RatingBar(
                rating = average,
                starsColor = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
fun SummarySection(
    overView: String
) {

    Column(
        modifier = Modifier.padding(MaterialTheme.dimen.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (overView.isNotEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.summary),
                    color = MaterialTheme.colors.primary,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Text(text = overView, fontSize = MaterialTheme.typography.body1.fontSize)
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
        }
    }
}

@Composable
fun SeasonsSection(
    seasons: List<ShortSeason>
) {

    seasons.forEach { season -> SeasonCard(season = season) }
}

@Composable
fun SeasonCard(
    season: ShortSeason
) {

    Card(
        modifier = Modifier
            .padding(MaterialTheme.dimen.small)
            .wrapContentSize(),
        shape = RoundedCornerShape(MaterialTheme.dimen.borderRounded),
        elevation = MaterialTheme.dimen.elevationNormal,
    ) {
        Row {
            season.poster_path?.let {
                ImageSection(imageUrl = it)
            }
            ContentSection(season = season)
        }
    }
}

@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    season: ShortSeason,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimen.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = season.name,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = MaterialTheme.typography.h6.fontSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            text = "${season.episode_count} Episodes"
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = season.overview,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontSize = MaterialTheme.typography.body1.fontSize
        )
    }
}

@Composable
private fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        modifier = modifier.size(MaterialTheme.dimen.imageLarge),
        model = ImageRequest.Builder(context)
            .data(imageUrl.getImageByPath())
            .crossfade(true)
            .build(),
        contentDescription = null,
        alignment = Alignment.TopStart,
        loading = { LinearProgressIndicator() },
        contentScale = ContentScale.FillWidth
    )
}
