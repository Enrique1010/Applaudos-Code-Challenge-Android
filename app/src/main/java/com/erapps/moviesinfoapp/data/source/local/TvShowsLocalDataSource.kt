package com.erapps.moviesinfoapp.data.source.local

import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.room.daos.MovieListDao
import com.erapps.moviesinfoapp.data.room.entities.MovieListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TvShowsLocalDataSource {
    suspend fun insertTvShows(movieListEntity: MovieListEntity)
    fun getCachedTvShows(): Flow<List<TvShow>>
    suspend fun clearCachedTvShows()
}

class TvShowsLocalDataSourceImp @Inject constructor(
    private val movieListDao: MovieListDao
) : TvShowsLocalDataSource {

    override suspend fun insertTvShows(movieListEntity: MovieListEntity) =
        withContext(Dispatchers.IO) {
            movieListDao.insertMovies(movieListEntity)
        }

    override fun getCachedTvShows(): Flow<List<TvShow>> = flow {
        emit(movieListDao.getCachedMovies()?.tvShows ?: emptyList())
    }.flowOn(Dispatchers.IO)

    override suspend fun clearCachedTvShows() = withContext(Dispatchers.IO) {
        movieListDao.clearMovies()
    }

}