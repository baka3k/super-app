package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.CastEntity
import com.baka3k.core.database.model.CrewEntity
import com.baka3k.core.network.model.NetworkCast
import com.baka3k.core.network.model.NetworkCrew

fun NetworkCast.asCastEntity(movieId: Long) = CastEntity(
    id = id,
    adult = adult,
    gender = gender.toInt(),
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity.toString(),
    profilePath = profilePath,
    castId = castID.toInt(),
    character = character,
    order = 0,
    creditId = creditID,
    movieId = movieId,
)

fun NetworkCrew.asCrewEntity(movieId: Long) = CrewEntity(
    id = id,
    adult = adult,
    gender = gender.toInt(),
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity.toString(),
    profilePath = profilePath,
    department = department,
    job = job,
    creditId = creditID,
    movieId = movieId,
)