package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.local.entities.ListConverter
import com.example.movieapp.data.local.entities.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_NAME = "movie_data_db"
    }
}