package com.erapps.moviesinfoapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.ui.screens.home.ImageSection
import com.erapps.moviesinfoapp.ui.screens.home.RatingSection
import com.erapps.moviesinfoapp.ui.screens.home.TitleSection
import com.erapps.moviesinfoapp.ui.theme.dimen

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val favsList by viewModel.favList.collectAsStateWithLifecycle()

    ProfileScreen(onBackPressed, favsList)
}

@Composable
private fun ProfileScreen(
    onBackPressed: () -> Unit,
    favsList: List<FavoriteTvShow>?
) {
    Scaffold(
        topBar = { ProfileTopBar(onBackPressed) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top
        ) {
            ProfileSection()
            FavoriteSection(favsList ?: emptyList())
            LogOutButtonSection()
        }
    }
}

@Composable
private fun ProfileTopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.profile), color = Color.White) },
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
fun LogOutButtonSection() {

    Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimen.medium)
                .size(MaterialTheme.dimen.mediumButtonSize),
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.logout_btn_text))
        }
    }
}

@Composable
fun FavoriteSection(favsList: List<FavoriteTvShow>) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimen.medium),
            text = stringResource(id = R.string.my_favs_title),
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold
        )
        if (favsList.isEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.no_favs_yet))
            }
        } else {
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            LazyRow(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimen.small)
            ) {
                items(favsList) { favTvShow ->
                    FavoritesListItem(favoriteTvShow = favTvShow)
                }
            }
        }
    }
}

@Composable
fun FavoritesListItem(
    modifier: Modifier = Modifier,
    favoriteTvShow: FavoriteTvShow
) {

    Card(
        modifier = modifier.padding(MaterialTheme.dimen.small),
        shape = RoundedCornerShape(MaterialTheme.dimen.borderRounded),
        elevation = MaterialTheme.dimen.elevationNormal
    ) {
        Column {
            ImageSection(imageUrl = favoriteTvShow.poster_path)
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Column(
                modifier = Modifier.padding(MaterialTheme.dimen.small)
            ) {
                TitleSection(tvShowName = favoriteTvShow.name)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                RatingSection(tvShowRating = favoriteTvShow.vote_average)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            }
        }
    }
}

@Composable
fun ProfileSection() {

    Spacer(modifier = Modifier.height(MaterialTheme.dimen.large))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimen.imageMedium)
                    .shadow(
                        elevation = MaterialTheme.dimen.roundedMedium,
                        shape = CircleShape,
                        clip = false,
                        ambientColor = MaterialTheme.colors.primary
                    ),
                imageVector = Icons.Default.Face,
                contentDescription = null
            )
            FloatingActionButton(
                modifier = Modifier.size(MaterialTheme.dimen.imageSmall),
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimen.medium),
                    imageVector = Icons.Default.Edit,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
        Text(
            text = stringResource(id = R.string.john_doe_name),
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        Text(
            text = stringResource(id = R.string.john_doe_username),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
    }
}
