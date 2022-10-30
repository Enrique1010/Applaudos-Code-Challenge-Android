package com.erapps.moviesinfoapp.data.source

import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.models.ErrorResponse
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.TvShowDetails
import com.erapps.moviesinfoapp.data.source.remote.TvShowDetailsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface TvShowDetailsRepository {
    fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>>
}

class TvShowDetailsRepositoryImp @Inject constructor(
    private val tvShowDetailsRemoteDataSource: TvShowDetailsRemoteDataSource
): TvShowDetailsRepository {

    override fun getTvShowDetails(id: Int): Flow<Result<TvShowDetails, ErrorResponse>> {
        return tvShowDetailsRemoteDataSource.getTvShowDetails(id).flowOn(Dispatchers.Default)
    }

}