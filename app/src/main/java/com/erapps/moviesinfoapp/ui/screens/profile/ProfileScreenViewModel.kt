package com.erapps.moviesinfoapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.data.source.local.TvShowDetailsLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val localDataSource: TvShowDetailsLocalDataSource
): ViewModel() {

    private val _favList = MutableStateFlow<List<FavoriteTvShow>?>(null)
    val favList = _favList.asStateFlow()

    init {
        getFavoritesTvShows()
    }

    private fun getFavoritesTvShows() = viewModelScope.launch {

        localDataSource.getFavsTvShows().collect { list ->
            when {
                list.isEmpty() -> { _favList.update { emptyList() } }
                else -> { _favList.update { list } }
            }
        }
    }
}