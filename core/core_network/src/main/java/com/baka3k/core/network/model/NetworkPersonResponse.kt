package com.baka3k.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPersonResponse(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("also_known_as")
    val alsoKnownAs: List<String> = emptyList(),
    @SerialName("biography")
    val biography: String = "",
    @SerialName("birthday")
    val birthday: String = "",
    @SerialName("deathday")
    val deathday: String = "",
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Long = 0,
    @SerialName("imdb_id")
    val imdbID: String = "",
    @SerialName("known_for_department")
    val knownForDepartment: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("place_of_birth")
    val placeOfBirth: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String = ""
)
