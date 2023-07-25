package com.example.movieapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.domain.model.Movie


@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    var id: Int,
    var adult: Boolean,
    var backdropPath: String,
    var genres: List<String>,
    var originalLanguage: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var title: String,
    var voteAverage: Double,
    var imdbUrl: String?,
    var revenue: Int?,
    var tagline: String?
)

fun MovieEntity.toMovie(): Movie = Movie(
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
    tagline = tagline,
    productionCompanies = null
)
