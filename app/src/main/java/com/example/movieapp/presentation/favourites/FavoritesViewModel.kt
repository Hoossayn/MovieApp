package com.example.movieapp.presentation.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.use_case.GetFavMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavMoviesUseCase: GetFavMoviesUseCase
) :
    ViewModel() {
    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>> get() = _favoriteMovies

    private var disposable: Disposable? = null

    init {
        getFavMovies()
    }


    private fun getFavMovies() {
        disposable = getFavMoviesUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ movies -> _favoriteMovies.value = movies }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}