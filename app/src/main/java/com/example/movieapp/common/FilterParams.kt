package com.example.movieapp.common


object FilterParams {
    val GENRES_FILTER = mapOf(
        "action" to 28,
        "adventure" to 12,
        "animation" to 16,
        "comedy" to 35,
        "crime" to 80,
        "documentary" to 99,
        "drama" to 18,
        "fantasy" to 14,
        "history" to 36,
        "horror" to 27,
        "mystery" to 9648,
        "romance" to 10749,
        "sci-fi" to 878,
        "thriller" to 53,
        "war" to 10752
    )
    val SORT_BY = mapOf(
        "popularity" to "popularity.desc",
        "vote" to "vote_average.desc",
        "date" to "primary_release_date.desc",
        "revenue" to "revenue.desc"
    )
}