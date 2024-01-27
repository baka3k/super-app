package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.database.model.MovieType
import com.baka3k.core.database.model.MovieTypeCrossRef
import com.baka3k.core.model.Movie
import com.baka3k.core.network.model.NetworkMovie


fun NetworkMovie.asEntity() = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    genreIDS = "emptyList()",
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun NetworkMovie.asMovieTypeEntity(movieType: Long) = MovieTypeCrossRef(
    movieId = id,
    typeId = movieType,
)

fun NetworkMovie.asUpStream() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)


fun NetworkMovie.asMovieTypePopularEntity() = asMovieTypeEntity(movieType = MovieType.POPULAR)
fun NetworkMovie.asMovieTypeNowPlayingEntity() =
    asMovieTypeEntity(movieType = MovieType.NOW_PLAYING)

fun NetworkMovie.asMovieTopRateEntity() = asMovieTypeEntity(movieType = MovieType.TOP_RATE)
fun NetworkMovie.asMovieUpCommingEntity() = asMovieTypeEntity(movieType = MovieType.UP_COMMING)
fun NetworkMovie.asTempMovieEntity() = asMovieTypeEntity(movieType = MovieType.OTHER)

fun NetworkMovie.asPopularMovie() = asUpStream()
fun NetworkMovie.asNowPlayingMovie() = asUpStream()
