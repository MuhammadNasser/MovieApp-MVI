package com.muhammad.movieappmvi

import androidx.paging.PagingData
import com.muhammad.movieappmvi.data.repository.MovieRepository
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.domain.usecase.GetMovieDetailsUseCase
import com.muhammad.movieappmvi.domain.usecase.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsUseCaseTest {
    private val repository = mockk<MovieRepository>()
    private val useCase = GetMovieDetailsUseCase(repository)

    @Test
    fun `invoke should return movie details`() = runTest {
        val movie = Movie(id = 1, title = "Test", isFavorite = false)
        coEvery { repository.getMovieDetails(1) } returns flowOf(movie)

        val result = useCase(1).first()

        assertEquals(movie, result)
        coVerify { repository.getMovieDetails(1) }
    }
}