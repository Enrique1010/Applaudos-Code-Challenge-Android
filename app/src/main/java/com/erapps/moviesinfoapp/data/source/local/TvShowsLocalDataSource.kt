package com.erapps.moviesinfoapp.data.source.local

import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.room.daos.TvShowListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TvShowsLocalDataSource {
    suspend fun insertTvShows(tvShow: TvShow)
    fun getCachedTvShows(): Flow<List<TvShow>?>
    suspend fun clearCachedTvShows()
}

class TvShowsLocalDataSourceImp @Inject constructor(
    private val tvShowListDao: TvShowListDao
) : TvShowsLocalDataSource {

    override suspend fun insertTvShows(tvShow: TvShow) =
        withContext(Dispatchers.IO) {
            tvShowListDao.insertTvShow(tvShow)
        }

    override fun getCachedTvShows(): Flow<List<TvShow>?> = flow {
        emit(tvShowListDao.getCachedTvShows())
    }.flowOn(Dispatchers.IO)

    override suspend fun clearCachedTvShows() = withContext(Dispatchers.IO) {
        tvShowListDao.clearTvShows()
    }

}