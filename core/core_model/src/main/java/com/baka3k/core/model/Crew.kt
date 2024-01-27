package com.baka3k.core.model

data class Crew(
    val id: Long = 0,
    val adult: Boolean,
    val gender: Int,
    val knownForDepartment: String = "",
    val name: String,
    val originalName: String,
    val popularity: String,
    val profilePath: String,
    val department: String,
    val job: String,
    val creditId: String,
    val movieId: Long,
)
