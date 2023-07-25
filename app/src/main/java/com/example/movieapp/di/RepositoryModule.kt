package com.example.movieapp.di

import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.remote.MoviesApi
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(moviesApi, movieDao)
    }

}