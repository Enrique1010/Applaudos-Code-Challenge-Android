package com.erapps.moviesinfoapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.source.local.TvShowsLocalDataSource
import com.erapps.moviesinfoapp.data.source.remote.TvShowsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface TvShowsRepository {
    fun getFilteredTvShows(filter: String): Flow<PagingData<TvShow>>

    //local data management
    suspend fun insertTvShows(tvShow: TvShow)
    fun getCachedTvShows(): Flow<List<TvShow>?>
    suspend fun clearCachedTvShows()
}

class TvShowsRepositoryImp @Inject constructor(
    private val tvShowsRemoteDataSource: TvShowsRemoteDataSource,
    private val tvShowsLocalDataSource: TvShowsLocalDataSource
): TvShowsRepository {

    override fun getFilteredTvShows(filter: String) = Pager(
    config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvShowsPagingSource(tvShowsRemoteDataSource, filter) }
    ).flow.flowOn(Dispatchers.Default)

    override suspend fun insertTvShows(tvShow: TvShow) {
        tvShowsLocalDataSource.insertTvShows(tvShow)
    }

    override fun getCachedTvShows(): Flow<List<TvShow>?> {
        return tvShowsLocalDataSource.getCachedTvShows()
    }

    override suspend fun clearCachedTvShows() {
        tvShowsLocalDataSource.clearCachedTvShows()
    }

}