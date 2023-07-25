package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.dto.MovieCreditDto
import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesListDto
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

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

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Flowable<MovieDetailDto>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Flowable<MoviesListDto>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredit(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String ): Flowable<MovieCreditDto>

    @GET("discover/movie")
    fun getFilteredMovies(@QueryMap queryMap: Map<String, String>, @Query("api_key") apiKey: String): Flowable<MoviesListDto>

}