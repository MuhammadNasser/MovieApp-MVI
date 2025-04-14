package com.muhammad.movieappmvi

import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.domain.usecase.GetMovieDetailsUseCase
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import com.muhammad.movieappmvi.presentation.details.MovieDetailsViewModel
import com.muhammad.movieappmvi.presentation.details.intent.MovieDetailsIntent
import com.muhammad.movieappmvi.presentation.details.state.MovieDetailsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {
    private val getMovieDetailsUseCase = mockk<GetMovieDetailsUseCase>()
    private val toggleFavoriteUseCase = mockk<ToggleFavoriteUseCase>(relaxed = true)
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, toggleFavoriteUseCase)
    }

    @Test
    fun `LoadMovieDetails updates state with Success`() = runTest {
        val movie = Movie(id = 1, title = "B", isFavorite = true)
        coEvery { getMovieDetailsUseCase(1) } returns flowOf(movie)

        viewModel.onEvent(MovieDetailsIntent.LoadMovieDetails(1))

        assert(viewModel.uiState.first() is MovieDetailsUiState.Success)
    }
}