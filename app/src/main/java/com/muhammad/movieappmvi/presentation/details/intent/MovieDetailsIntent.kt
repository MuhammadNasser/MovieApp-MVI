package com.muhammad.movieappmvi.presentation.details.intent

import com.muhammad.movieappmvi.domain.model.Movie

sealed class MovieDetailsIntent {
    data class LoadMovieDetails(val movieId: Int) : MovieDetailsIntent()
    data class ToggleFavorite(val movie: Movie) : MovieDetailsIntent()
}
