package com.example.movieapp.domain.model

import com.example.movieapp.common.FilterParams


data class MoviesFilter(
    var sortBy: String,
    var genres: List<String>,
    var voteGte: Float,
    var voteLte: Float,
    var yearGte: Float,
    var yearLte: Float
)

fun MoviesFilter.toQueryMap(): Map<String, String> = mapOf(
    "sort_by" to FilterParams.SORT_BY[sortBy] as String,
    "with_genres" to genres.map { FilterParams.GENRES_FILTER[it]}.joinToString(separator = ","),
    "vote_average.gte" to voteGte.toString(),
    "vote_average.lte" to voteLte.toString(),
    "release_date.gte" to yearGte.toInt().toString().plus("-01-01"),
    "release_date.lte" to yearLte.toInt().toString().plus("-12-31"),
).filterNot { it.value.isEmpty() }
