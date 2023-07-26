package com.example.movieapp.domain.use_case

import com.example.movieapp.common.MoviesListType
import com.example.movieapp.common.Resource
import com.example.movieapp.data.remote.dto.toMoviesList
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GetMoviesListUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    private val result = PublishSubject.create<Resource<MoviesList>>()
    private val mCompositeDisposable = CompositeDisposable()

    operator fun invoke(listType: MoviesListType): Flowable<Resource<MoviesList>> {
        val apiResponse = movieRepository.getMoviesList(listType)
        result.onNext(Resource.loading(null))
        val response =  apiResponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(5, TimeUnit.SECONDS)
            .subscribe { response ->
                try {
                    result.onNext(Resource.success(response.toMoviesList()))
                } catch (e: HttpException){
                    result.onNext(Resource.error(null, e.localizedMessage ?: "An unexpected error occurred"))
                } catch (e: IOException) {
                    result.onNext(Resource.error(null, "Couldn't reach server. Check your internet connection."))

                }
            }
        mCompositeDisposable.add(response)
        return result.toFlowable(BackpressureStrategy.BUFFER)

    }
}