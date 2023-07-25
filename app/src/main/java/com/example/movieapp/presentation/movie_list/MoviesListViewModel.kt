package com.example.movieapp.presentation.movie_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movieapp.common.MoviesListType
import com.example.movieapp.common.Resource
import com.example.movieapp.domain.model.MoviesList
import com.example.movieapp.domain.use_case.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _moviesList = MutableLiveData<Resource<MoviesList>>()
    val moviesList: LiveData<Resource<MoviesList>> get() = _moviesList

    private var disposable: Disposable? = null

    init {
        val listType = savedStateHandle.get<MoviesListType>("listType")
        Log.d("MoviesListViewModel", "Arg: $listType")
        getMoviesList(listType!!)
    }

    private fun getMoviesList(listType: MoviesListType) {
        disposable = getMoviesListUseCase(listType)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _moviesList.value = it }
    }
}