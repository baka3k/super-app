package com.baka3k.core.network.model

import com.baka3k.core.network.model.util.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisode(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val newsResources: List<String> = listOf(),
    val authors: List<String> = listOf(),
)

/**
 * Network representation of [Episode] when fetched from /episodes/{id}
 */
@Serializable
data class NetworkEpisodeExpanded(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String,
    val alternateAudio: String,
    val newsResources: List<NetworkNewsResource> = listOf(),
    val authors: List<NetworkAuthor> = listOf(),
)
