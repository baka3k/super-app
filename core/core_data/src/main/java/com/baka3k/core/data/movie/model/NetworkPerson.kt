package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.PersonEntity
import com.baka3k.core.network.model.NetworkPersonResponse


fun NetworkPersonResponse.asEntity() = PersonEntity(
    id = id,
    adult = adult,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    gender = gender,
    homepage = homepage,
    imdbId = imdbID,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    alsoKnowAs = buildAlsoKnowAs(alsoKnownAs)
)

fun buildAlsoKnowAs(alsoKnowAs: List<String>): String {
    return if (alsoKnowAs.isEmpty()) {
        ""
    } else if (alsoKnowAs.size == 1) {
        alsoKnowAs[0]
    } else {
        val stringBuilder = StringBuilder()
        alsoKnowAs.forEach {
            stringBuilder.append(it)
            stringBuilder.append("|")
        }
        return stringBuilder.substring(0, stringBuilder.lastIndexOf("|"))
    }
}