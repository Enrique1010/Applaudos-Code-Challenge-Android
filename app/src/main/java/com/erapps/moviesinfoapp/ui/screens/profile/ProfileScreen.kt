package com.erapps.moviesinfoapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.ui.screens.home.ImageSection
import com.erapps.moviesinfoapp.ui.screens.home.RatingSection
import com.erapps.moviesinfoapp.ui.screens.home.TitleSection
import com.erapps.moviesinfoapp.ui.shared.WindowSizeClass
import com.erapps.moviesinfoapp.ui.shared.rememberWindowSize
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
    val windowSize = rememberWindowSize()

    Scaffold(
        topBar = { ProfileTopBar(windowSize, onBackPressed) }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                ProfileSection(windowSize)
                FavoriteSection(windowSize, favsList ?: emptyList())
                LogOutButtonSection(windowSize)
            }
        }
    }
}

@Composable
private fun ProfileTopBar(
    windowSize: WindowSizeClass,
    onBackPressed: () -> Unit
) {

    val windowSizeCondition = windowSize.screenWidthInfo is WindowSizeClass.WindowType.Compact
    val fontSize =
        if (windowSizeCondition) MaterialTheme.typography.h6.fontSize else MaterialTheme.typography.h4.fontSize
    val appBarHeight =
        if (windowSizeCondition) MaterialTheme.dimen.appBarNormal else MaterialTheme.dimen.appBarLarge
    val iconButtonSize =
        if (windowSizeCondition) MaterialTheme.dimen.large else MaterialTheme.dimen.extraLarge
    val iconSize =
        if (windowSizeCondition) MaterialTheme.dimen.smallMedium else MaterialTheme.dimen.mediumLarge

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(appBarHeight),
        title = {
            Text(
                text = stringResource(id = R.string.profile),
                color = Color.White,
                fontSize = fontSize
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.size(iconButtonSize),
                onClick = onBackPressed
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
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
fun LogOutButtonSection(windowSize: WindowSizeClass) {

    val windowSizeCondition = windowSize.screenWidthInfo is WindowSizeClass.WindowType.Compact
    val fontSize =
        if (windowSizeCondition) MaterialTheme.typography.subtitle1.fontSize else MaterialTheme.typography.h5.fontSize

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
            Text(
                text = stringResource(id = R.string.logout_btn_text),
                fontSize = fontSize,
                color = Color.White
            )
        }
    }
}

@Composable
fun FavoriteSection(windowSize: WindowSizeClass, favsList: List<FavoriteTvShow>) {

    val windowSizeCondition = windowSize.screenWidthInfo is WindowSizeClass.WindowType.Compact
    val labelFontSize =
        if (windowSizeCondition) MaterialTheme.typography.h6.fontSize else MaterialTheme.typography.h4.fontSize
    val fontSize =
        if (windowSizeCondition) MaterialTheme.typography.subtitle1.fontSize else MaterialTheme.typography.h5.fontSize

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimen.medium),
            text = stringResource(id = R.string.my_favs_title),
            fontSize = labelFontSize,
            fontWeight = FontWeight.Bold
        )
        if (favsList.isEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_favs_yet),
                    fontSize = fontSize
                )
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

    val windowSize = rememberWindowSize()

    Card(
        modifier = modifier
            .padding(MaterialTheme.dimen.small)
            .size(width = MaterialTheme.dimen.imageExtraLarge, height = 244.dp),
        shape = RoundedCornerShape(MaterialTheme.dimen.borderRounded),
        elevation = MaterialTheme.dimen.elevationNormal
    ) {
        Column {
            ImageSection(imageUrl = favoriteTvShow.poster_path)
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Column(
                modifier = Modifier.padding(MaterialTheme.dimen.small)
            ) {
                TitleSection(tvShowName = favoriteTvShow.name, windowSize = windowSize)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                RatingSection(tvShowRating = favoriteTvShow.vote_average)
            }
        }
    }
}

@Composable
fun ProfileSection(windowSize: WindowSizeClass) {

    val windowSizeCondition = windowSize.screenWidthInfo is WindowSizeClass.WindowType.Compact
    val iconSize =
        if (windowSizeCondition) MaterialTheme.dimen.imageMedium else MaterialTheme.dimen.imageLarge
    val fabSize =
        if (windowSizeCondition) MaterialTheme.dimen.imageSmall else MaterialTheme.dimen.appBarNormal
    val nameFontSize =
        if (windowSizeCondition) MaterialTheme.typography.body1.fontSize else MaterialTheme.typography.h4.fontSize
    val userNameFontSize =
        if (windowSizeCondition) MaterialTheme.typography.body2 else MaterialTheme.typography.h6

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
                    .size(iconSize)
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
                modifier = Modifier.size(fabSize),
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
            fontSize = nameFontSize
        )
        Text(
            text = stringResource(id = R.string.john_doe_username),
            style = userNameFontSize
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
    }
}