package com.baka3k.core.network.datasource

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.PagingInfo
import com.baka3k.core.network.model.NetworkMovie

interface MovieNetworkDataSource {
    suspend fun getPopularMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>>
    suspend fun getTopRateMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>>
    suspend fun getUpCommingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>>
    suspend fun getNowPlayingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>>
    suspend fun findMovie(query:String,pagingInfo: PagingInfo): Result<List<NetworkMovie>>
}