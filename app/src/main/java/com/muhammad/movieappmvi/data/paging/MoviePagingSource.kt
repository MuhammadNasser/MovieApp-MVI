package com.muhammad.movieappmvi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muhammad.movieappmvi.BuildConfig.TMDB_API_KEY
import com.muhammad.movieappmvi.data.remote.api.MovieApiService
import com.muhammad.movieappmvi.data.remote.dto.MovieDto

class MoviePagingSource(
    private val apiService: MovieApiService
) : PagingSource<Int, MovieDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getMovies(TMDB_API_KEY, page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}