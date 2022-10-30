package com.erapps.moviesinfoapp.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.data.source.TvShowDetailsRepository
import com.erapps.moviesinfoapp.ui.shared.UiState
import com.erapps.moviesinfoapp.utils.Constants.TV_SHOW_ID_ARGUMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TvShowDetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites = _isInFavorites.asStateFlow()

    init {
        savedStateHandle.get<Int>(TV_SHOW_ID_ARGUMENT)?.let { tvShowId ->
            getTvShowDetails(tvShowId)
            tvShowIsInFavorites(tvShowId)
        }
    }

    private fun getTvShowDetails(id: Int) = viewModelScope.launch {
        repository.getTvShowDetails(id).collect { result ->
            when (result) {
                is Result.Error -> _uiState.update { UiState.Error(errorStringResource = R.string.no_info_about_tv_show) }
                Result.Loading -> _uiState.update { UiState.Loading }
                is Result.Success -> _uiState.update {
                    UiState.Success(result.data)
                }
            }
        }
    }

    fun tvShowIsInFavorites(id: Int) = viewModelScope.launch {
        repository.getFavId(id).collect { dbId ->
            when (dbId) {
                id -> _isInFavorites.update { true }
                null -> _isInFavorites.update { false }
            }
        }
    }

    fun insertFavoriteTvShow(tvShow: FavoriteTvShow) = viewModelScope.launch {
        repository.insertFavTvShow(tvShow)
    }

    fun deleteFavoriteTvShow(id: Int) = viewModelScope.launch {
        repository.deleteFavTvShow(id)
    }
}