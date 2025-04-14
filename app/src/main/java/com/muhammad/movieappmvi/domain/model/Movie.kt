package com.muhammad.movieappmvi.domain.model

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val isFavorite: Boolean = false,
    val overview: String = "",
    val backdropPath: String = "",
    val voteAverage: Double = 0.0,
    val genres: String = ""
)