package com.muhammad.movieappmvi.data.mapper

import com.muhammad.movieappmvi.data.local.entities.CachedMovieEntity
import com.muhammad.movieappmvi.data.local.entities.MovieEntity
import com.muhammad.movieappmvi.data.remote.dto.MovieDetailsDto
import com.muhammad.movieappmvi.data.remote.dto.MovieDto
import com.muhammad.movieappmvi.domain.model.Movie
import com.muhammad.movieappmvi.utils.Constants.IMAGE_BASE_URL

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = "$IMAGE_BASE_URL$posterPath",
    releaseDate = releaseDate.orEmpty()
)

fun MovieEntity.toDomain(): Movie =
    Movie(
        id = id ?: 0,
        title = title.orEmpty(),
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        isFavorite = true,
        overview = overview.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        genres = genres.orEmpty()
    )

fun Movie.toEntity(): MovieEntity =
    MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        isFavorite = true,
        overview = overview,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        genres = genres
    )

fun MovieDetailsDto.toDomain(isFavorite: Boolean = false): Movie {
    return Movie(
        id = id,
        title = title,
        isFavorite = isFavorite,
        overview = overview.orEmpty(),
        posterPath = "$IMAGE_BASE_URL${posterPath.orEmpty()}",
        backdropPath = "$IMAGE_BASE_URL${backdropPath.orEmpty()}",
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage,
        genres = genres.joinToString(", ") { it.name }
    )
}

fun Movie.toCachedEntity(): CachedMovieEntity = CachedMovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    isFavorite = isFavorite,
    overview = overview,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    genres = genres
)

fun CachedMovieEntity.toDomain(): Movie = Movie(
    id = id ?: 0,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    isFavorite = isFavorite ?: false,
    overview = overview.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    voteAverage = voteAverage ?: 0.0,
    genres = genres.orEmpty()
)
