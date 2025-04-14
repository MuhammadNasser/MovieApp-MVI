package com.muhammad.movieappmvi

import com.muhammad.movieappmvi.data.repository.MovieRepository
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleFavoriteUseCaseTest {
    private val repository = mockk<MovieRepository>(relaxed = true)
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `invoke should call toggleFavorite`() = runTest {
        val movie = Movie(id = 1, title = "Movie", isFavorite = false)

        useCase(movie)

        coVerify { repository.toggleFavorite(movie) }
    }
}