package com.erapps.moviesinfoapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.models.TvShow
import com.erapps.moviesinfoapp.data.source.remote.TvShowsRemoteDataSource

class TvShowsPagingSource(
    private val dataSource: TvShowsRemoteDataSource,
    private val filter: String
) : PagingSource<Int, TvShow>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return when (val result = dataSource.getFilteredTvShows(filter = filter, page = position)) {
            is Result.Error -> LoadResult.Error(Exception(result.data?.status_message))
            is Result.Loading -> LoadResult.Error(Exception())
            is Result.Success -> {
                LoadResult.Page(
                    data = result.data!!.results,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (result.data.results.isEmpty()) null else position + 1
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}