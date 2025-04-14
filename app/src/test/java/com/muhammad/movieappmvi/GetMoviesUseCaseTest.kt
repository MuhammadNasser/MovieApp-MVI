package com.muhammad.movieappmvi

import androidx.paging.PagingData
import com.muhammad.movieappmvi.data.repository.MovieRepository
import com.muhammad.movieappmvi.domain.model.Movie
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
class GetMoviesUseCaseTest {
    private val repository = mockk<MovieRepository>()
    private val useCase = GetMoviesUseCase(repository)

    @Test
    fun `invoke should return paging data`() = runTest {
        val fakePaging = PagingData.from(listOf(Movie(id = 1, title = "Movie", isFavorite = false)))
        coEvery { repository.getMovies(1) } returns flowOf(fakePaging)

        val result = useCase(1).first()

        assertEquals(fakePaging, result)
        coVerify { repository.getMovies(1) }
    }
}