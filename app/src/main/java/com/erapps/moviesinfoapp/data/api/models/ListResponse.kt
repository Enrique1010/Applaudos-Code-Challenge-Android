package com.erapps.moviesinfoapp.data.api.models

data class ListResponse(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)