package com.erapps.moviesinfoapp.data.source

import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.models.ErrorResponse
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.TvShowDetails
import com.erapps.moviesinfoapp.data.room.entities.FavoriteTvShow
import com.erapps.moviesinfoapp.data.source.local.TvShowDetailsLocalDataSource
import com.erapps.moviesinfoapp.data.source.remote.TvShowDetailsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface TvShowDetailsRepository {
    fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>>

    //local management methods
    fun getFavsTvShows(): Flow<List<FavoriteTvShow>>
    suspend fun insertFavTvShow(tvShow: FavoriteTvShow)
    fun getFavId(id: Int): Flow<Int?>
    suspend fun deleteFavTvShow(id: Int)
}

class TvShowDetailsRepositoryImp @Inject constructor(
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource,
    private val tvShowDetailsLocalDataSource: TvShowDetailsLocalDataSource
) : TvShowDetailsRepository {

    //remote
    override fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>> {
        return tvShowDetailsRemoteDataSource.getTvShowDetails(id).flowOn(Dispatchers.Default)
    }

    //local
    override fun getFavsTvShows(): Flow<List<FavoriteTvShow>> {
        return tvShowDetailsLocalDataSource.getFavsTvShows()
    }

    override suspend fun insertFavTvShow(tvShow: FavoriteTvShow) {
        tvShowDetailsLocalDataSource.insertFavTvShow(tvShow)
    }

    override fun getFavId(id: Int): Flow<Int?> {
        return tvShowDetailsLocalDataSource.getFavId(id)
    }

    override suspend fun deleteFavTvShow(id: Int) {
        tvShowDetailsLocalDataSource.deleteFavTvShow(id)
    }

}