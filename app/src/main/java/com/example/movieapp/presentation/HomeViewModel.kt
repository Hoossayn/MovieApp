package com.example.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.MoviesListType
import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.use_case.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _popularMoviesList = MutableLiveData<Resource<MoviesList>>()
    private val _nowPlayingMoviesList = MutableLiveData<Resource<MoviesList>>()
    private val _upcomingMoviesList = MutableLiveData<Resource<MoviesList>>()
    private val _topRatedMoviesList = MutableLiveData<Resource<MoviesList>>()
    private val _moviesListType = MutableLiveData<MoviesListType>()
    val popularMoviesList: LiveData<Resource<MoviesList>> get() = _popularMoviesList
    val nowPlayingMoviesList: LiveData<Resource<MoviesList>> get() = _nowPlayingMoviesList
    val upcomingMoviesList: LiveData<Resource<MoviesList>> get() = _upcomingMoviesList
    val topRatedMoviesList: LiveData<Resource<MoviesList>> get() = _topRatedMoviesList
    val moviesListType: LiveData<MoviesListType> get() = _moviesListType

    private var disposable: Disposable? = null

    init {
        getPopularMoviesList()
        getNowPlayingMoviesList()
        getUpcomingMoviesList()
        getTopRatedMoviesList()
    }

    fun setMoviesListType(listType: MoviesListType) {
        _moviesListType.value = listType
    }

    private fun getPopularMoviesList() {
        disposable = getMoviesListUseCase(MoviesListType.POPULAR)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ _popularMoviesList.value = it }
    }

    private fun getNowPlayingMoviesList() {
        disposable = getMoviesListUseCase(MoviesListType.NOW_PLAYING)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _nowPlayingMoviesList.value = it }
    }

    private fun getUpcomingMoviesList() {
        disposable = getMoviesListUseCase(MoviesListType.UPCOMING)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _upcomingMoviesList.value = it }
    }

    private fun getTopRatedMoviesList() {
        disposable = getMoviesListUseCase(MoviesListType.TOP_RATED)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _topRatedMoviesList.value = it }
    }

}