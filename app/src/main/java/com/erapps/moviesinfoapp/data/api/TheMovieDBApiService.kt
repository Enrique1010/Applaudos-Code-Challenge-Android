package com.erapps.moviesinfoapp.data.api

import com.erapps.moviesinfoapp.BuildConfig
import com.erapps.moviesinfoapp.data.api.models.FilterBySelection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApiService {

    @GET("tv/{filterBy}")
    suspend fun getTrendingTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Path("filterBy") filterBy: String = FilterBySelection.Popular.filter
    )

    //tv show details api calls
    @GET("tv/{id}")
    suspend fun getTVShowById(
        @Path("id") id: Int
    )

    @GET("tv/{id}/season/{season}")
    suspend fun getTVShowSeason(
        @Path("id") id: Int,
        @Path("season") season: Int
    )
}