package com.muhammad.movieappmvi.data.local.source

import com.muhammad.movieappmvi.data.local.entities.MovieEntity
import com.muhammad.movieappmvi.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun addToFavorites(movie: MovieEntity)

    suspend fun removeFromFavorites(id: Int)

    suspend fun isFavorite(id: Int): Boolean

    fun getCachedMovies(): Flow<List<Movie>>

    suspend fun cacheMovies(movies: List<Movie>)

    suspend fun getCachedMovieDetails(id: Int): MovieEntity?
}