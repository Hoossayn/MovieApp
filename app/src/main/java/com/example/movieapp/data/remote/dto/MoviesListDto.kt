package com.example.movieapp.data.remote.dto


import com.example.movieapp.domain.model.MoviesList
import com.google.gson.annotations.SerializedName

data class MoviesListDto(
    val page: Int,
    @SerializedName("results")
    val moviesDto: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun MoviesListDto.toMoviesList(): MoviesList = MoviesList(
    page = page,
    moviesDto.map { it.toMovie() },
    totalPages = totalPages
)