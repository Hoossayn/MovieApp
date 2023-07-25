package com.example.movieapp.data.remote.dto

import com.example.movieapp.common.Constants
import com.example.movieapp.common.MovieGenre
import com.example.movieapp.domain.model.Movie
import com.google.gson.annotations.SerializedName


data class MovieDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun MovieDto.toMovie(): Movie = Movie(
    id = id,
    adult = adult,
    backdropPath = Constants.BASE_MOVIE_IMAGE_URL.plus(backdropPath),
    genreIds.map { MovieGenre.GENRES[it] ?: "" },
    originalLanguage = originalLanguage,
    overview = overview,
    posterPath = Constants.BASE_MOVIE_IMAGE_URL.plus(posterPath),
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    imdbUrl = null,
    revenue = null,
    tagline = null,
    productionCompanies = null
)