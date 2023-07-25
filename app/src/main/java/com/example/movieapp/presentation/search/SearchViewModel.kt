package com.example.movieapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.MoviesFilter
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.use_case.GetFilteredMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase,
) : ViewModel() {

    private val _moviesFilter = MutableLiveData<MoviesFilter>()
    private val _filteredMovies = MutableLiveData<Resource<MoviesList>>()
    private var disposable: Disposable? = null
    val moviesFilter: LiveData<MoviesFilter> get() = _moviesFilter
    val filteredMovies: LiveData<Resource<MoviesList>> get() = _filteredMovies

    fun setFilter(filter: MoviesFilter) {
        _moviesFilter.value = filter
    }

    fun searchMovies() {
        if (moviesFilter.value != null)
            disposable = getFilteredMoviesUseCase.invoke(moviesFilter.value!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _filteredMovies.value = it }



    }

}