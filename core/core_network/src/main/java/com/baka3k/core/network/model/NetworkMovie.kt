package com.baka3k.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMovie(
    val adult: Boolean =false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Long> = emptyList(),
    val id: Long = 0,
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Long = 0
)
@Serializable
data class NetworkMovieResponse(
    val page: Long = 0 ,
    val results: List<NetworkMovie> = emptyList(),
    val totalPages: Long = 0,
    val totalResults: Long = 0
)
