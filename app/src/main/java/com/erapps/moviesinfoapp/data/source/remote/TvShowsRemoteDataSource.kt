package com.erapps.moviesinfoapp.data.source.remote

import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.TheMovieDBApiService
import com.erapps.moviesinfoapp.data.api.models.ErrorResponse
import com.erapps.moviesinfoapp.data.api.models.ListResponse
import com.erapps.moviesinfoapp.data.source.mapResponse
import javax.inject.Inject

interface TvShowsRemoteDataSource {
    suspend fun getFilteredTvShows(filter: String, page: Int): Result<ListResponse, ErrorResponse>
}

class TvShowsRemoteDataSourceImp @Inject constructor(
    private val apiService: TheMovieDBApiService
) : TvShowsRemoteDataSource {

    override suspend fun getFilteredTvShows(filter: String, page: Int) = mapResponse {
        apiService.getTrendingTvShows(filterBy = filter, page = page)
    }
}