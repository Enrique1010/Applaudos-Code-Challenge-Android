package com.erapps.moviesinfoapp.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.api.models.FilterBySelection
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.api.models.getAllFilters
import com.erapps.moviesinfoapp.data.api.models.getFilter
import com.erapps.moviesinfoapp.ui.shared.PageWithState
import com.erapps.moviesinfoapp.ui.shared.RatingBar
import com.erapps.moviesinfoapp.ui.shared.UiState
import com.erapps.moviesinfoapp.ui.shared.getNetworkStatus
import com.erapps.moviesinfoapp.ui.theme.dimen
import com.erapps.moviesinfoapp.utils.getImageByPath
import com.google.accompanist.pager.*
import java.math.RoundingMode

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onFavsClick: () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()
    val netWorkStatus = getNetworkStatus()

    val uiState = when {
        tvShows.loadState.source.refresh == LoadState.Loading -> {
            UiState.Loading
        }
        tvShows.itemCount == 0 -> {
            if (!netWorkStatus) {
                viewModel.getLocalListOfTvShows()
                UiState.Success(tvShows)
            }
            UiState.Empty
        }
        else -> {
            UiState.Success(tvShows)
        }
    }

    HomeScreen(
        uiState = uiState,
        onEmptyButtonClick = { viewModel.getFilteredTvShows(FilterBySelection.Popular.filter) },
        onFavsClick = onFavsClick,
        onCardClick = onCardClick,
        onCache = { viewModel.cacheTvShows(it) }
    ) { viewModel.getFilteredTvShows(it) }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState?,
    onFavsClick: () -> Unit,
    onEmptyButtonClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onCache: (List<TvShow>) -> Unit,
    onFilterSelected: (String) -> Unit,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { AppBar(onFavsClick) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ListAndFilter(onFilterSelected, uiState, onEmptyButtonClick, onCardClick, onCache)
        }
    }
}

@Composable
private fun ListAndFilter(
    onFilterSelected: (String) -> Unit,
    uiState: UiState?,
    onEmptyButtonClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onCache: (List<TvShow>) -> Unit
) {

    Column {
        FilterChipGroup(onFilterSelected = onFilterSelected)
        HomeScreenContent(
            uiState = uiState,
            onEmptyButtonClick = onEmptyButtonClick,
            onCache = onCache,
            onCardClick = onCardClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FilterChipGroup(
    modifier: Modifier = Modifier,
    filters: List<FilterBySelection> = getAllFilters(),
    onFilterSelected: (String) -> Unit
) {

    val selectedFilter = rememberSaveable { mutableStateOf(getFilter("popular")) }

    Column(modifier = modifier.padding(MaterialTheme.dimen.small)) {
        LazyRow {
            items(filters) { filter ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimen.extraSmall),
                    selected = selectedFilter.value == filter,
                    onClick = {
                        selectedFilter.value = getFilter(filter.filter)
                        onFilterSelected(filter.filter)
                    },
                    shape = CircleShape,
                    selectedIcon = {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                    },
                    colors = ChipDefaults.filterChipColors(
                        selectedBackgroundColor = MaterialTheme.colors.primary,
                        selectedContentColor = Color.White
                    )
                ) {
                    Text(text = filter.filter.capitalize(Locale.current).replace("_", " "))
                }
            }
        }
    }
}

@Composable
private fun HomeScreenContent(
    uiState: UiState?,
    onEmptyButtonClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onCache: (List<TvShow>) -> Unit
) {
    val context = LocalContext.current
    val status = getNetworkStatus()

    PageWithState<LazyPagingItems<TvShow>>(
        uiState = uiState,
        onClick = onEmptyButtonClick
    ) {
        TvShowList(it, onCache) { id ->
            //only can go to details if internet is available
            if (status) {
                onCardClick(id)
                return@TvShowList
            }
            Toast.makeText(
                context,
                context.getString(R.string.cant_see_details_without_internet_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
private fun TvShowList(
    tvShows: LazyPagingItems<TvShow>,
    onCache: (List<TvShow>) -> Unit,
    onCardClick: (Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(tvShows.itemCount) { i ->
            onCache(listOf(tvShows[i]!!))
            TvShowListItem(tvShow = tvShows[i]!!, onCardClick = onCardClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TvShowListItem(
    modifier: Modifier = Modifier,
    tvShow: TvShow,
    onCardClick: (Int) -> Unit
) {

    Card(
        modifier = modifier.padding(MaterialTheme.dimen.small),
        shape = RoundedCornerShape(MaterialTheme.dimen.borderRounded),
        elevation = MaterialTheme.dimen.elevationNormal,
        onClick = { onCardClick(tvShow.id) }
    ) {
        Column {
            ImageSection(imageUrl = tvShow.poster_path)
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Column(
                modifier = Modifier.padding(MaterialTheme.dimen.small)
            ) {
                TitleSection(tvShowName = tvShow.name)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                RatingSection(tvShowRating = tvShow.vote_average)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            }
        }
    }
}

@Composable
private fun RatingSection(
    modifier: Modifier = Modifier,
    tvShowRating: Double
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RatingBar(rating = (tvShowRating / 2), starsColor = MaterialTheme.colors.secondary)
        Text(
            text = (tvShowRating / 2).toBigDecimal()
                .setScale(1, RoundingMode.UP)
                .toDouble()
                .toString()
        )
    }
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier,
    tvShowName: String
) {

    Text(
        modifier = modifier.fillMaxWidth(),
        text = tvShowName.capitalize(Locale.current),
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Start,
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ImageSection(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        modifier = modifier.size(MaterialTheme.dimen.imageExtraLarge),
        model = ImageRequest.Builder(context)
            .data(imageUrl.getImageByPath())
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error_placeholder)
            .crossfade(true)
            .build(),
        contentDescription = null,
        alignment = Alignment.Center,
        loading = { LinearProgressIndicator() },
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun AppBar(onFavsClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.tv_shows_title),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = onFavsClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}