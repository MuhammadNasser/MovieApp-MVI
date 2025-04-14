package com.muhammad.movieappmvi.domain.usecase

import androidx.paging.PagingData
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): Flow<PagingData<Movie>> {
        return repository.getMovies(page)
    }
}