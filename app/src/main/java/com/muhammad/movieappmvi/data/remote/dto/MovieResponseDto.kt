package com.muhammad.movieappmvi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?
)