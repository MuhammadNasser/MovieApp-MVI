package com.muhammad.movieappmvi.presentation.home.state

import androidx.paging.PagingData
import com.muhammad.movieappmvi.domain.model.Movie

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val movies: PagingData<Movie>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}