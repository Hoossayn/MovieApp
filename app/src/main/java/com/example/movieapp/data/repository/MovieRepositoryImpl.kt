package com.example.movieapp.data.repository


import com.example.movieapp.common.MoviesListType
import com.example.movieapp.common.MoviesListType.*
import com.example.movieapp.common.Secrets
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.data.remote.MoviesApi
import com.example.movieapp.data.remote.dto.MovieCreditDto
import com.example.movieapp.data.remote.dto.MovieDetailDto
import com.example.movieapp.data.remote.dto.MoviesListDto
import com.example.movieapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDao: MovieDao
) : MovieRepository {

    // remote
    override fun getMovieById(id: Int): Flowable<MovieDetailDto> {
        return moviesApi.getMovieDetail(id,Secrets.APIKEY)
    }

    override fun getMoviesList(listType: MoviesListType): Flowable<MoviesListDto> {
        return when (listType) {
            NOW_PLAYING -> moviesApi.getNowPlayingMovies(Secrets.APIKEY)
            POPULAR -> moviesApi.getPopularMovies(Secrets.APIKEY)
            TOP_RATED -> moviesApi.getTopRatedMovies(Secrets.APIKEY)
            UPCOMING -> moviesApi.getUpComingMovies(Secrets.APIKEY)
        }
    }

    override fun getSimilarMovies(id: Int): Flowable<MoviesListDto> {
        return moviesApi.getSimilarMovies(id, Secrets.APIKEY)
    }

    override fun getMovieCredit(id: Int): Flowable<MovieCreditDto> {
        return moviesApi.getMovieCredit(id, Secrets.APIKEY)
    }

    override fun getFilteredMovies(queryMap: Map<String, String>): Flowable<MoviesListDto> {
        return moviesApi.getFilteredMovies(queryMap, Secrets.APIKEY)
    }

    // local
    override suspend fun getFavMovieById(id: Int): MovieEntity {
        return movieDao.getMovie(id)
    }

    override fun getFavMovies(): Flowable<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    override fun addMovieToFav(movieEntity: MovieEntity) = movieDao.insertMovie(movieEntity)

    override fun removeFavMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)

}