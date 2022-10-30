package com.erapps.moviesinfoapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
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
        topBar = {
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileSection()
            FavoriteSection(favsList ?: emptyList())
        }
    }
}

@Composable
fun FavoriteSection(favsList: List<FavoriteTvShow>) {

    Text(text = "My Favorites")
    if (favsList.isEmpty()) {
        Text(text = "Nothing to show")
    } else {
        LazyRow {
            items(favsList) { favTvShow ->
                Text(text = favTvShow.name)
            }
        }
    }

}

@Composable
fun ProfileSection() {

    Column {
        Icon(imageVector = Icons.Default.Face3, contentDescription = null)
        Text(text = "John Doe")
        Text(text = "@john_doe")
        Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
    }
}
