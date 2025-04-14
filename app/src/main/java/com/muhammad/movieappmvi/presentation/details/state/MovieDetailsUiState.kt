package com.muhammad.movieappmvi.presentation.details.state

import com.muhammad.movieappmvi.domain.model.Movie

sealed class MovieDetailsUiState {
    object Idle : MovieDetailsUiState()
    object Loading : MovieDetailsUiState()
    data class Success(val movie: Movie) : MovieDetailsUiState()
    data class Error(val message: String) : MovieDetailsUiState()
}
