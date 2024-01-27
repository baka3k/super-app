package com.baka3k.core.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.network.model.NetworkPersonResponse

interface PersonNetworkDataSource {
    suspend fun getPerson(personId: String): Result<NetworkPersonResponse>
}