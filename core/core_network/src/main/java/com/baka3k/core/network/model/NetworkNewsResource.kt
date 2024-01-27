package com.baka3k.core.network.model

import com.baka3k.core.model.NewsResourceType
import com.baka3k.core.network.model.util.InstantSerializer
import com.baka3k.core.network.model.util.NewsResourceTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


/**
 * Network representation of [NewsResource] when fetched from /newsresources
 */
@Serializable
data class NetworkNewsResource(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    @Serializable(NewsResourceTypeSerializer::class)
    val type: NewsResourceType,
    val authors: List<String> = listOf(),
    val topics: List<String> = listOf(),
)

/**
 * Network representation of [NewsResource] when fetched from /newsresources/{id}
 */
@Serializable
data class NetworkNewsResourceExpanded(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    @Serializable(NewsResourceTypeSerializer::class)
    val type: NewsResourceType,
    val authors: List<NetworkAuthor> = listOf(),
    val topics: List<NetworkTopic> = listOf(),
)
