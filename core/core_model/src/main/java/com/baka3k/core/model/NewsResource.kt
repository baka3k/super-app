package com.baka3k.core.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class NewsResource(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: NewsResourceType,
    val authors: List<Author>,
    val topics: List<Topic>
)

val previewNewsResources = listOf(
    NewsResource(
        id = "1",
        episodeId = "60",
        title = "Android Basics with Compose",
        content = "We released the first two units of Android Basics with Compose, our first free course that teaches Android Development with Jetpack Compose to anyone; you do not need any prior programming experience other than basic computer literacy to get started. You’ll learn the fundamentals of programming in Kotlin while building Android apps using Jetpack Compose, Android’s modern toolkit that simplifies and accelerates native UI development. These two units are just the beginning; more will be coming soon. Check out Android Basics with Compose to get started on your Android development journey",
        url = "https://android-developers.googleblog.com/2022/05/new-android-basics-with-compose-course.html",
        headerImageUrl = "https://developer.android.com/images/hero-assets/android-basics-compose.svg",
        authors = listOf(previewAuthors[0]),
        publishDate = LocalDateTime(
            year = 2022,
            monthNumber = 5,
            dayOfMonth = 4,
            hour = 23,
            minute = 0,
            second = 0,
            nanosecond = 0
        ).toInstant(TimeZone.UTC),
        type = NewsResourceType.Codelab,
        topics = listOf(previewTopics[1])
    )
)
