package com.muhammad.movieappmvi.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "poster")
    val posterPath: String?,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String?,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "backdrop")
    val backdropPath: String?,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double?,
    @ColumnInfo(name = "genres")
    val genres: String?
)