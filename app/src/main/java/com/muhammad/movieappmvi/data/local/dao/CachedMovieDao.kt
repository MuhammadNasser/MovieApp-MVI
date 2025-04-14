package com.muhammad.movieappmvi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammad.movieappmvi.data.local.entities.CachedMovieEntity
import com.muhammad.movieappmvi.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedMovieDao {

    @Query("SELECT * FROM cached_movies")
    fun getCachedMovies(): Flow<List<CachedMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedMovies(movies: List<CachedMovieEntity>)

    @Query("DELETE FROM cached_movies")
    suspend fun clearCachedMovies()

    @Query("SELECT * FROM cached_movies WHERE id = :id")
    suspend fun getCachedMovie(id: Int): MovieEntity?
}