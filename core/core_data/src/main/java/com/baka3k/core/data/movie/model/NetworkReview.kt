package com.baka3k.core.data.movie.model

import com.baka3k.core.database.model.ReviewEntity
import com.baka3k.core.network.model.NetworkReview


fun NetworkReview.asEntity(movieId: Long) = ReviewEntity(
    id = id,
    authorName = author,
    authorUserName = authorDetails?.username ?: "",
    avatarPath = authorDetails?.avatarPath ?: "",
    rating = (authorDetails?.rating ?: 0).toInt(),
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    url = url,
    movie_id = movieId
)