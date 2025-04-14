package com.muhammad.movieappmvi.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.movieappmvi.domain.usecase.GetMovieDetailsUseCase
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import com.muhammad.movieappmvi.presentation.details.intent.MovieDetailsIntent
import com.muhammad.movieappmvi.presentation.details.state.MovieDetailsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Idle)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    fun onEvent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.LoadMovieDetails -> {
                fetchMovieDetails(intent.movieId)
            }

            is MovieDetailsIntent.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(intent.movie)
                    fetchMovieDetails(intent.movie.id)
                }
            }
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailsUiState.Loading
            getMovieDetailsUseCase(movieId)
                .collect { movie ->
                    _uiState.value = MovieDetailsUiState.Success(movie)
                }
        }
    }
}