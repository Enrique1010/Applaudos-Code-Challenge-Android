package com.erapps.moviesinfoapp.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.ui.shared.PageWithState
import com.erapps.moviesinfoapp.ui.shared.UiState
import com.erapps.moviesinfoapp.ui.theme.dimen
import com.erapps.moviesinfoapp.utils.convertDpToSp
import com.erapps.moviesinfoapp.utils.getImageByPath
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()
    val uiState: UiState = if (tvShows.itemCount == 0) {
        UiState.Empty
    } else {
        UiState.Success(tvShows)
    }

    HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState?
) {

    Scaffold(
        modifier = modifier,
        topBar = { AppBar() }
    ) { paddingValues ->

        PageWithState<LazyPagingItems<TvShow>>(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues)
        ) { TvShowList(it) }
    }
}

@Composable
fun TvShowList(
    tvShows: LazyPagingItems<TvShow>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(tvShows.itemCount) { i ->
            TvShowListItem(tvShow = tvShows[i]!!)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowListItem(
    modifier: Modifier = Modifier,
    tvShow: TvShow
) {

    Card(
        modifier = modifier.padding(MaterialTheme.dimen.small),
        shape = RoundedCornerShape(MaterialTheme.dimen.borderRounded),
        elevation = MaterialTheme.dimen.elevationNormal,
        onClick = {}
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
fun RatingSection(
    modifier: Modifier = Modifier,
    tvShowRating: Double
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RatingBar(rating = (tvShowRating / 2), starsColor = MaterialTheme.colors.primary)
        Text(text = (tvShowRating / 2).toString())
    }
}

/*
* Widget based on Angelo Rüggeber rating bar widget
* Source: https://www.jetpackcompose.app/snippets/RatingBar
* Author: Angelo Rüggeber modified by Luis Enrique Ramirez
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color,
) {

    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }

        if (halfStar) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }

        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
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
        fontSize = MaterialTheme.dimen.textSmall.convertDpToSp(),
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
fun AppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_title),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun TvShowListItemPreview() {
    //TvShowListItem()
}