package com.muhammad.movieappmvi.presentation.home.intent

import com.muhammad.movieappmvi.domain.model.Movie

sealed class HomeIntent {
    object FetchMovies : HomeIntent()
    data class ToggleFavorite(val movie: Movie) : HomeIntent()
    data class ChangeLayout(val isGrid: Boolean) : HomeIntent()
}