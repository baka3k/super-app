package com.baka3k.core.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.network.model.NetworkCreditsResponse

interface CreditNetworkDataSource {
    suspend fun getCredits(movieId: Long): Result<NetworkCreditsResponse>
}