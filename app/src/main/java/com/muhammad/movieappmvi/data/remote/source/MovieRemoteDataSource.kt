package com.muhammad.movieappmvi.data.remote.source

import androidx.paging.PagingData
import com.muhammad.movieappmvi.data.remote.dto.MovieDetailsDto
import com.muhammad.movieappmvi.data.remote.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun fetchMovies(page: Int): Flow<PagingData<MovieDto>>
    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsDto
}