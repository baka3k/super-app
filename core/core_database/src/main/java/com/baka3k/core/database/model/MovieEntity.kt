package com.baka3k.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baka3k.core.model.Movie

@Entity(
    tableName = "movies",
)
data class MovieEntity(
    @PrimaryKey
    val id: Long = 0,
    @ColumnInfo(defaultValue = "false")
    val adult: Boolean,
    @ColumnInfo(defaultValue = "")
    val backdropPath: String,
    @ColumnInfo(defaultValue = "")
//    val genreIDS: List<Long> = emptyList(),
    val genreIDS: String = "",
    @ColumnInfo(defaultValue = "")
    val originalLanguage: String,
    @ColumnInfo(defaultValue = "")
    val originalTitle: String,
    @ColumnInfo(defaultValue = "")
    val overview: String,
    @ColumnInfo(defaultValue = "0.0")
    val popularity: Double,
    @ColumnInfo(defaultValue = "")
    val posterPath: String,
    @ColumnInfo(defaultValue = "")
    val releaseDate: String,
    @ColumnInfo(defaultValue = "")
    val title: String,
    @ColumnInfo(defaultValue = "false")
    val video: Boolean,
    @ColumnInfo(defaultValue = "0.0")
    val voteAverage: Double,
    @ColumnInfo(defaultValue = "0")
    val voteCount: Long
)

fun MovieEntity.asExternalModel() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)
