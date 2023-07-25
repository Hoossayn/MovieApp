package com.example.movieapp.domain.use_case

import com.example.movieapp.data.local.entities.toMovie
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class GetFavMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(): Flowable<List<Movie>> {
        return movieRepository.getFavMovies()
            .subscribeOn(Schedulers.io())
            .map { entities ->
                entities.map { it.toMovie() }
            }
    }
}