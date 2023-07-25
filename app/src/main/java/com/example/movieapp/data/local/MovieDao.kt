package com.example.movieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entities.MovieEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity): Completable

    @Delete
    fun deleteMovie(movieEntity: MovieEntity): Completable

    @Query("select * from movie_table")
    fun getMovies(): Flowable<List<MovieEntity>>

    @Query("select * from movie_table where id = :id")
    suspend fun getMovie(id: Int): MovieEntity
}