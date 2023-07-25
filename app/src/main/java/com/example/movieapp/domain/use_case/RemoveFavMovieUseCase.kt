package com.example.movieapp.domain.use_case

import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.toMovieEntity
import com.example.movieapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class RemoveFavMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    private val result = PublishSubject.create<Resource<Boolean>>()
    private val mCompositeDisposable = CompositeDisposable()

    operator fun invoke(movie: Movie): Flowable<Resource<Boolean>> {
        val localDbResponse = movieRepository.removeFavMovie(movie.toMovieEntity())
            .subscribeOn(Schedulers.io())
            .subscribe()

        mCompositeDisposable.add(localDbResponse)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
}