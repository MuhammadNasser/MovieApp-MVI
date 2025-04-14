package com.muhammad.movieappmvi

import androidx.paging.PagingData
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.domain.usecase.GetMoviesUseCase
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import com.muhammad.movieappmvi.presentation.home.HomeViewModel
import com.muhammad.movieappmvi.presentation.home.intent.HomeIntent
import com.muhammad.movieappmvi.presentation.home.state.HomeUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val getMoviesUseCase = mockk<GetMoviesUseCase>()
    private val toggleFavoriteUseCase = mockk<ToggleFavoriteUseCase>(relaxed = true)
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getMoviesUseCase, toggleFavoriteUseCase)
    }

    @Test
    fun `fetchMovies updates uiState with success`() = runTest {
        val pagingData = PagingData.from(listOf(Movie(id = 1, title = "A", isFavorite = false)))
        coEvery { getMoviesUseCase(1) } returns flowOf(pagingData)

        viewModel.onIntent(HomeIntent.FetchMovies)

        assert(viewModel.uiState.first() is HomeUiState.Success)
    }
}