package com.muhammad.movieappmvi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.muhammad.movieappmvi.domain.usecase.GetMoviesUseCase
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import com.muhammad.movieappmvi.presentation.home.intent.HomeIntent
import com.muhammad.movieappmvi.presentation.home.state.HomeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _isGrid = MutableStateFlow(true)
    val isGrid: StateFlow<Boolean> = _isGrid.asStateFlow()

    private val _favoriteToggled = MutableStateFlow<Int?>(null)
    val favoriteToggled = _favoriteToggled.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchMovies -> {
                fetchMovies()
            }

            is HomeIntent.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(intent.movie)
                    _favoriteToggled.value = intent.movie.id
                }
            }

            is HomeIntent.ChangeLayout -> {
                _isGrid.value = intent.isGrid
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            getMoviesUseCase(1)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _uiState.value = HomeUiState.Success(pagingData)
                }
        }
    }
}