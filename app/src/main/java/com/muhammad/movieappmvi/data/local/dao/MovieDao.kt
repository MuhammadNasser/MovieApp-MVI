package com.muhammad.movieappmvi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammad.movieappmvi.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteMovie(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}