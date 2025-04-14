package com.muhammad.movieappmvi.data.repository

import androidx.paging.PagingData
import com.muhammad.movieappmvi.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(page: Int): Flow<PagingData<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun getMovieDetails(movieId: Int): Flow<Movie>
    suspend fun getCachedMovies(): Flow<List<Movie>>
}