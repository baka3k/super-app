package com.baka3k.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkReviewResponse(
    @SerialName("id")
    val id: Long = 0,
    @SerialName("page")
    val page: Long = 0,
    @SerialName("results")
    val results: List<NetworkReview> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Long = 0,
    @SerialName("total_results")
    val totalResults: Long = 0
)

@Serializable
data class NetworkReview(
    @SerialName("author")
    val author: String = "",
    @SerialName("author_details")
    val authorDetails: AuthorDetails?,
    @SerialName("content")
    val content: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("updated_at")
    val updatedAt: String = "",
    @SerialName("url")
    val url: String = ""
)

@Serializable
data class AuthorDetails(
    @SerialName("name")
    val name: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("avatar_path")
    val avatarPath: String = "",
    @SerialName("rating")
    val rating: Double = 0.0
)
