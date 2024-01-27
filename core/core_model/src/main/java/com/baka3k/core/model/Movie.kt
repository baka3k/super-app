package com.baka3k.core.model

data class Movie(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val id: Long = 0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Long = 0,
    val genres:MutableList<Genre> = mutableListOf()
)

data class Type(val id: Long = 0, val title: String = "")
data class Genre(val id: Long = 0, val name: String = "")