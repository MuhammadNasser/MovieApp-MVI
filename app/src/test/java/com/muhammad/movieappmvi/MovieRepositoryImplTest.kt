package com.muhammad.movieappmvi

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.muhammad.movieappmvi.core.NetworkChecker
import com.muhammad.movieappmvi.data.local.source.MovieLocalDataSource
import com.muhammad.movieappmvi.data.remote.source.MovieRemoteDataSource
import com.muhammad.movieappmvi.data.repository.MovieRepositoryImpl
import com.muhammad.movieappmvi.domain.model.Movie
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {
    private val remote = mockk<MovieRemoteDataSource>()
    private val local = mockk<MovieLocalDataSource>()
    private val networkChecker = mockk<NetworkChecker>()
    private val repository = MovieRepositoryImpl(remote, local, networkChecker)

    @Test
    fun `getMovies returns cached when offline`() = runTest {
        // Set up mock responses
        val cached = listOf(Movie(id = 1, title = "Cached", isFavorite = true))
        every { networkChecker.hasInternetConnection() } returns false
        coEvery { local.getCachedMovies() } returns flowOf(cached)

        // Call the repository method inside the TestScope
        val result = repository.getMovies(1).first().collectData()

        // Assert the result
        assertEquals(cached, result)

        // Advance the coroutines to ensure all tasks are completed
        advanceUntilIdle()
    }

    private suspend fun PagingData<Movie>.collectData(): List<Movie> {
        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieDiffCallback(),
            updateCallback = NoopListCallback,
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.IO
        )
        differ.submitData(this)
        return differ.snapshot().items
    }

    object NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}