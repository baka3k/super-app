package com.baka3k.core.data.movie.repository

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun pullMovieReviewsFromNetwork(movieId: Long): Result<Int>
    fun getMovieReviewsStream(movieId: Long): Flow<List<Review>>
}