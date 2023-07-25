package com.example.movieapp.data.remote.dto


data class MovieCreditDto(
    val id: Int,
    var cast: List<CastDto>
)