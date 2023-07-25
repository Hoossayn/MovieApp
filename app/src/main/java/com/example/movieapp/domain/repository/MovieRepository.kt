package com.example.movieapp.domain.repository


import com.example.movieapp.common.MoviesListType
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.data.remote.dto.MovieCreditDto
import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesListDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


interface MovieRepository {

    // remote
    fun getMovieById(id: Int): Flowable<MovieDetailDto>
    fun getMoviesList(listType: MoviesListType): Flowable<MoviesListDto>
    fun getSimilarMovies(id: Int): Flowable<MoviesListDto>
    fun getMovieCredit(id: Int): Flowable<MovieCreditDto>
    fun getFilteredMovies(queryMap: Map<String, String>): Flowable<MoviesListDto>


    // local
    suspend fun getFavMovieById(id: Int): MovieEntity
    fun getFavMovies(): Flowable<List<MovieEntity>>
    fun addMovieToFav(movieEntity: MovieEntity): Completable
    fun removeFavMovie(movieEntity: MovieEntity): Completable

}