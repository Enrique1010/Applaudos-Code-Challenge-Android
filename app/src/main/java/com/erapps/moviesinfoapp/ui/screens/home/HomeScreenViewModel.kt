package com.erapps.moviesinfoapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erapps.moviesinfoapp.data.api.models.FilterBySelection
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.room.entities.MovieListEntity
import com.erapps.moviesinfoapp.data.source.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: TvShowsRepository
) : ViewModel() {

    private val _tvShows = MutableStateFlow<PagingData<TvShow>>(PagingData.empty())
    val tvShows = _tvShows.asStateFlow()

    init {
        getFilteredTvShows(FilterBySelection.Popular.filter)
    }

    fun getFilteredTvShows(filter: String) = viewModelScope.launch {
        repository.getFilteredTvShows(filter)
            .cachedIn(viewModelScope)
            .collectLatest { tvShows ->
                _tvShows.update { tvShows }
            }
    }

    fun getLocalListOfTvShows() = viewModelScope.launch {

        repository.getCachedTvShows().collect {
            val tvShows = it?.tvShows ?: emptyList()

            _tvShows.update { PagingData.from(tvShows) }
        }
    }

    fun cacheTvShows(tvShows: List<TvShow>) = viewModelScope.launch {
        var oldestTimestamp = System.currentTimeMillis()

        repository.getCachedTvShows().collect { movieListEntity ->
            movieListEntity?.let {
                oldestTimestamp = it.timestamp
            }

            //refresh list every 20 minutes or if pokemonList is empty (eg: First time in the app without internet)
            val needsRefresh =
                oldestTimestamp < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(20)
                        || movieListEntity?.tvShows.isNullOrEmpty()

            if (needsRefresh) {
                if (tvShows.size > 30) {
                    //repository.clearCachedTvShows()
                    repository.insertTvShows(MovieListEntity(tvShows = tvShows))
                    return@collect
                }
            }
        }
    }
}