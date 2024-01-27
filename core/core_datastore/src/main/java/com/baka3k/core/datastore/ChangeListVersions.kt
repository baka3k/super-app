package com.baka3k.core.datastore

data class ChangeListVersions(
    val movieVersion: Int = -1,
    val movieTypeVersion: Int = -1,
    val topicVersion: Int = -1,
    val authorVersion: Int = -1,
    val episodeVersion: Int = -1,
    val newsResourceVersion: Int = -1,
)