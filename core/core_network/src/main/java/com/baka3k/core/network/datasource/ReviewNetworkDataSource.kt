package com.baka3k.core.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.network.model.NetworkReview

interface ReviewNetworkDataSource {
    suspend fun getReview(movieId: Long): Result<List<NetworkReview>>
}