package com.baka3k.core.model

import kotlinx.datetime.Instant

data class Episode(
    val id: String,
    val name: String,
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val newsResources: List<NewsResource>,
    val authors: List<Author>
)
