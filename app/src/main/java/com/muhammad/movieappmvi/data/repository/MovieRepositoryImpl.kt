package com.muhammad.movieappmvi.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.muhammad.movieappmvi.core.NetworkChecker
import com.muhammad.movieappmvi.data.local.source.MovieLocalDataSource
import com.muhammad.movieappmvi.data.mapper.toDomain
import com.muhammad.movieappmvi.data.mapper.toEntity
import com.muhammad.movieappmvi.data.remote.source.MovieRemoteDataSource
import com.muhammad.movieappmvi.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val networkChecker: NetworkChecker
) : MovieRepository {
    private var isFirstLoadDone = false

    override suspend fun getMovies(page: Int): Flow<PagingData<Movie>> {
        val firstPageMovies = mutableListOf<Movie>()

        return if (networkChecker.hasInternetConnection()) {
            remoteDataSource.fetchMovies(page).map { pagingData ->
                pagingData.map { dto ->
                    val isFav = localDataSource.isFavorite(dto.id)
                    val movie = dto.toDomain().copy(isFavorite = isFav)

                    if (page == 1 && !isFirstLoadDone && firstPageMovies.size < 20) {
                        firstPageMovies.add(movie)
                        if (firstPageMovies.size == 20) {
                            isFirstLoadDone = true
                            localDataSource.cacheMovies(firstPageMovies)
                        }
                    }

                    movie
                }
            }
        } else {
            localDataSource.getCachedMovies()
                .map { PagingData.from(it) }
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies()
    }

    override suspend fun toggleFavorite(movie: Movie) = withContext(Dispatchers.IO) {
        if (movie.isFavorite) {
            localDataSource.removeFromFavorites(movie.id)
        } else {
            localDataSource.addToFavorites(movie.toEntity())
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Movie> = flow {
        if (networkChecker.hasInternetConnection()) {
            val movieDto = remoteDataSource.fetchMovieDetails(movieId)
            val isFavorite = localDataSource.isFavorite(movieId)
            val movie = movieDto.toDomain(isFavorite)

            emit(movie)
        } else {
            val cached = localDataSource.getCachedMovieDetails(movieId)
            if (cached != null) {
                emit(cached.toDomain())
            } else {
                throw IOException("No internet connection and no cached data available.")
            }
        }
    }

    override suspend fun getCachedMovies(): Flow<List<Movie>> =
        localDataSource.getCachedMovies()

}