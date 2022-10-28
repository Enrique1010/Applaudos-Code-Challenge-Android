package com.erapps.moviesinfoapp.data.api.models

enum class FilterBySelection(val filter: String) {
    Popular("popular"),
    TopRated("top_rated"),
    OnTV("on_the_air"),
    AiringToday("airing_today")
}