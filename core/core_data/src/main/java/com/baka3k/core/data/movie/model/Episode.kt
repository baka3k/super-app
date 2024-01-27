package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.EpisodeEntity
import com.baka3k.core.network.model.NetworkEpisode
import com.baka3k.core.network.model.NetworkEpisodeExpanded


fun NetworkEpisode.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)

fun NetworkEpisodeExpanded.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)
