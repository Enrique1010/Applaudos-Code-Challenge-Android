package com.erapps.moviesinfoapp.data.api

import com.erapps.moviesinfoapp.BuildConfig
import com.erapps.moviesinfoapp.data.api.models.ErrorResponse
import com.erapps.moviesinfoapp.data.api.models.FilterBySelection
import com.erapps.moviesinfoapp.data.api.models.ListResponse
import com.erapps.moviesinfoapp.data.api.models.tvshowdetails.TvShowDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApiService {

    @GET("tv/{filterBy}")
    suspend fun getTrendingTvShows(
        @Path("filterBy") filterBy: String = FilterBySelection.Popular.filter,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int

    ): NetworkResponse<ListResponse, ErrorResponse>

    //tv show details api calls
    @GET("tv/{id}")
    suspend fun getTVShowById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): NetworkResponse<TvShowDetails, ErrorResponse>
}