package com.muhammad.movieappmvi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammad.movieappmvi.data.local.dao.CachedMovieDao
import com.muhammad.movieappmvi.data.local.dao.MovieDao
import com.muhammad.movieappmvi.data.local.entities.CachedMovieEntity
import com.muhammad.movieappmvi.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class, CachedMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun cachedMovieDao(): CachedMovieDao
}