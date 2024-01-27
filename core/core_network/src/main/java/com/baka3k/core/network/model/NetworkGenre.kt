package com.baka3k.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkGenre(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("name")
    val name: String = "",
)
@Serializable
data class NetworkGenreResponse(
    val genres: List<NetworkGenre> = emptyList(),
)