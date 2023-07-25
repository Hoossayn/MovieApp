package com.example.movieapp.presentation.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.Cast
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.use_case.AddMovieToFavUseCase
import com.example.movieapp.domain.use_case.GetFavMovieUseCase
import com.example.movieapp.domain.use_case.GetMovieCastUseCase
import com.example.movieapp.domain.use_case.GetMovieDetailUseCase
import com.example.movieapp.domain.use_case.GetSimilarMoviesUseCase
import com.example.movieapp.domain.use_case.RemoveFavMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val addMovieToFavUseCase: AddMovieToFavUseCase,
    private val removeFavMovieUseCase: RemoveFavMovieUseCase,
    private val getFavMovieUseCase: GetFavMovieUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<Movie>>()
    private val _movieCast = MutableLiveData<Resource<List<Cast>>>()
    private val _similarMovies = MutableLiveData<Resource<MoviesList>>()
    private val _favMovie = MutableLiveData<Movie>()
    private val _isFav = MutableLiveData<Boolean>()

    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    val movieDetail: LiveData<Resource<Movie>> get() = _movieDetail
    val movieCast: LiveData<Resource<List<Cast>>> get() = _movieCast
    val similarMovies: LiveData<Resource<MoviesList>> get() = _similarMovies
    val favMovie: LiveData<Movie> get() = _favMovie
    val isFav: LiveData<Boolean> get() = _isFav

    init {
        val movieId = savedStateHandle.get<Int>("movieId")
        val mFavMovie = savedStateHandle.get<Movie>("movie")
        if (movieId != null) {
            getMovieDetail(movieId)
            checkIfFav(movieId)
            getSimilarMovies(movieId)
            getMovieCast(movieId)
        } else {
            _isFav.value = true
            getMovieDetail(mFavMovie!!)
            getSimilarMovies(mFavMovie.id)
            getMovieCast(mFavMovie.id)
        }
    }

    private fun getMovieDetail(movieId: Int) {
        disposable = getMovieDetailUseCase(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _movieDetail.value = it }
    }

    private fun getMovieDetail(movie: Movie) {
        _favMovie.value = movie
    }

    private fun getSimilarMovies(movieId: Int) {
        disposable = getSimilarMoviesUseCase(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _similarMovies.value = it }
    }

    private fun getMovieCast(movieId: Int) {
        disposable = getMovieCastUseCase(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _movieCast.value = it }
    }


    fun addMovieToFav(movie: Movie) {

        disposable = addMovieToFavUseCase(movie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _isFav.value = it.data!!
            }
    }

    fun removeMovieFromFav(movie: Movie) {
        disposable = removeFavMovieUseCase(movie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _isFav.value = !it.data!!
            }
    }

    private fun checkIfFav(movieId: Int) {
        getFavMovieUseCase(movieId).onEach { resource ->
            _isFav.value = resource.status == Resource.Status.SUCCESS && resource.data != null
        }
            .launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}