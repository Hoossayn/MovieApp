package com.example.movieapp.domain.model

import android.os.Parcelable
import com.example.movieapp.data.local.entities.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val imdbUrl: String?,
    val revenue: Int?,
    val tagline: String?,
    val productionCompanies: List<ProductionCompany>?
) : Parcelable

fun Movie.toMovieEntity(): MovieEntity = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    genres = genres,
    originalLanguage = originalLanguage,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    imdbUrl = imdbUrl,
    revenue = revenue,
    tagline = tagline
)

