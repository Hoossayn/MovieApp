package com.example.movieapp.domain.use_case

import com.example.movieapp.common.Resource
import com.example.movieapp.data.local.entities.toMovie
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke(id: Int): Flow<Resource<Movie>> = flow {
        try {
            val favMovie = movieRepository.getFavMovieById(id).toMovie()
            emit(Resource.success(favMovie))
        } catch (e: Exception) {
            emit(Resource.error(null, e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}