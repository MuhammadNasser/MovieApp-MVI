package com.muhammad.movieappmvi.data.local.source

import com.muhammad.movieappmvi.data.local.dao.CachedMovieDao
import com.muhammad.movieappmvi.data.local.dao.MovieDao
import com.muhammad.movieappmvi.data.local.entities.MovieEntity
import com.muhammad.movieappmvi.data.mapper.toCachedEntity
import com.muhammad.movieappmvi.data.mapper.toDomain
import com.muhammad.movieappmvi.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao,
    private val cachedMovieDao: CachedMovieDao
) : MovieLocalDataSource {
    override fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.getFavorites().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun addToFavorites(movie: MovieEntity) = movieDao.insert(movie)

    override suspend fun removeFromFavorites(id: Int) = movieDao.deleteMovie(id)

    override suspend fun isFavorite(id: Int): Boolean {
        return movieDao.isFavorite(id)
    }

    override fun getCachedMovies(): Flow<List<Movie>> =
        cachedMovieDao.getCachedMovies().map { list -> list.map { it.toDomain() } }

    override suspend fun cacheMovies(movies: List<Movie>) {
        cachedMovieDao.clearCachedMovies()
        val firstTwenty = movies.take(20)
        cachedMovieDao.insertCachedMovies(firstTwenty.map { it.toCachedEntity() })
    }

    override suspend fun getCachedMovieDetails(id: Int): MovieEntity? =
        cachedMovieDao.getCachedMovie(id)
}