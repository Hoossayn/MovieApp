package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.dto.MoviesListDto
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Flowable<MoviesListDto>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Flowable<MoviesListDto>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Flowable<MoviesListDto>

    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("api_key") apiKey: String
    ): Flowable<MoviesListDto>

}