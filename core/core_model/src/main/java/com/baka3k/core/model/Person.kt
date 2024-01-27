package com.baka3k.core.model

data class Person(
    val adult: Boolean = false,
    val alsoKnownAs: List<String> = emptyList(),
    val biography: String = "",
    val birthday: String = "",
    val deathday: String = "",
    val gender: Int = 0,
    val homepage: String = "",
    val id: Long = 0,
    val imdbID: String = "",
    val knownForDepartment: String = "",
    val name: String = "",
    val placeOfBirth: String = "",
    val popularity: Double = 0.0,
    val profilePath: String = ""
)