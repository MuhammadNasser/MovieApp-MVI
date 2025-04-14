package com.muhammad.movieappmvi.domain.usecase

import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.data.repository.MovieRepository

class ToggleFavoriteUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.toggleFavorite(movie)
    }
}