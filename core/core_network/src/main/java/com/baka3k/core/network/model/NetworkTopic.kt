package com.baka3k.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTopic(
    val id: String,
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val followed: Boolean = false,
)
