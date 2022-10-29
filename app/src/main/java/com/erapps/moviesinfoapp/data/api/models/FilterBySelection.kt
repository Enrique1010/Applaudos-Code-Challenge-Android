package com.erapps.moviesinfoapp.data.api.models

enum class FilterBySelection(val filter: String) {
    Popular("popular"),
    TopRated("top_rated"),
    OnTV("on_the_air"),
    AiringToday("airing_today")
}

fun getAllFilters(): List<FilterBySelection> {
    return listOf(
        FilterBySelection.Popular,
        FilterBySelection.TopRated,
        FilterBySelection.OnTV,
        FilterBySelection.AiringToday,
    )
}

fun getFilter(value: String): FilterBySelection? {
    val map = FilterBySelection.values().associateBy(FilterBySelection::filter)
    return map[value]
}