package com.erapps.moviesinfoapp.data.source.remote

import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.TheMovieDBApiService
import com.erapps.moviesinfoapp.data.api.models.ErrorResponse
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.TvShowDetails
import com.erapps.moviesinfoapp.data.source.mapResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TvShowDetailsRemoteDataSource {
    fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>>
}

class TvShowDetailsRemoteDataSourceImp @Inject constructor(
    private val apiService: TheMovieDBApiService
) : TvShowDetailsRemoteDataSource {

    override fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>> =
        mapResponse(Dispatchers.IO) {
            apiService.getTVShowById(id)
        }
}