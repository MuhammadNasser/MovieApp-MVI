package com.muhammad.movieappmvi.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammad.movieappmvi.BuildConfig.TMDB_API_KEY
import com.muhammad.movieappmvi.data.paging.MoviePagingSource
import com.muhammad.movieappmvi.data.remote.api.MovieApiService
import com.muhammad.movieappmvi.data.remote.dto.MovieDetailsDto
import com.muhammad.movieappmvi.data.remote.dto.MovieDto
import kotlinx.coroutines.flow.Flow

class MovieRemoteDataSourceImpl(
    private val api: MovieApiService
) : MovieRemoteDataSource {

    override suspend fun fetchMovies(page: Int): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(api) }
        ).flow
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsDto =
        api.getMovieDetails(movieId, TMDB_API_KEY)
}