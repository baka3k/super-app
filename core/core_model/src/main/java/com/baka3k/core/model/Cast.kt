package com.baka3k.core.model

data class Cast(
    val id: Long = 0,
    val adult: Boolean,
    val gender: Int,
    val knownForDepartment: String = "",
    val name: String,
    val originalName: String,
    val popularity: String,
    val profilePath: String,
    val castId: Int,
    val character: String,
    val order: Int,
    val creditId: String,
    val movieId: Long,
)
