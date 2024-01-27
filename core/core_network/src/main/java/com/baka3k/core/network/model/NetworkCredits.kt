package com.baka3k.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCast(
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("gender") val gender: Long = 0L,
    @SerialName("id") val id: Long = 0L,
    @SerialName("known_for_department") val knownForDepartment: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("original_name") val originalName: String = "",
    @SerialName("popularity") val popularity: Double = 0.0,
    @SerialName("profile_path") val profilePath: String = "",
    @SerialName("cast_id") val castID: Long = 0,
    @SerialName("character") val character: String = "",
    @SerialName("credit_id") val creditID: String,
)
@Serializable
data class NetworkCrew(
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("gender") val gender: Long = 0L,
    @SerialName("id") val id: Long = 0L,
    @SerialName("known_for_department") val knownForDepartment: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("original_name") val originalName: String = "",
    @SerialName("popularity") val popularity: Double = 0.0,
    @SerialName("profile_path") val profilePath: String = "",
    @SerialName("credit_id") val creditID: String,
    @SerialName("department") val department: String = "",
    @SerialName("job") val job: String = ""
)

@Serializable
data class NetworkCreditsResponse(
    @SerialName("id") val id: Long,
    @SerialName("cast") val cast: List<NetworkCast>,
    @SerialName("crew") val crew: List<NetworkCrew>
)
