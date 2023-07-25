package com.example.movieapp.data.remote.dto


import com.example.movieapp.common.Constants
import com.example.movieapp.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {

    data class Genre(
        val id: Int,
        val name: String
    )


}


fun MovieDetailDto.toMovie(): Movie = Movie(
    id = id,
    adult = adult,
    backdropPath = Constants.BACKDROP_MOVIE_IMAGE_URL.plus(backdropPath),
    genres.map { it.name },
    originalLanguage = originalLanguage,
    overview = overview,
    posterPath = Constants.BASE_MOVIE_IMAGE_URL.plus(posterPath),
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    imdbUrl = Constants.IMDB_MOVIE_BASE_URL.plus(imdbId),
    revenue = revenue,
    tagline = tagline,
    productionCompanies.map { it.toProdCompany() }
)