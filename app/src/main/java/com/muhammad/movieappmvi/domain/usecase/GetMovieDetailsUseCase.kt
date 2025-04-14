package com.muhammad.movieappmvi.domain.usecase

import com.muhammad.movieappmvi.data.repository.MovieRepository
import com.muhammad.movieappmvi.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<Movie> {
        return repository.getMovieDetails(movieId)
    }
}