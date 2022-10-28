package com.erapps.moviesinfoapp.data.api.models.tvshowdetails

data class ShortSeason(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)