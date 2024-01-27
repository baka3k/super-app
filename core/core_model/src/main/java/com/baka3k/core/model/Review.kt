package com.baka3k.core.model

data class Review(
    val id: Long = 0,
    val authorName: String = "",
    val authorUserName: String = "",
    val avatarPath: String = "",
    val rating: Int = 0,
    val content: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val url: String = "",
)