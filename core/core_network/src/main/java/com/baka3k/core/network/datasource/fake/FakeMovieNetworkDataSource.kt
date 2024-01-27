package com.baka3k.core.network.datasource.fake

import android.annotation.SuppressLint
import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.common.result.Result
import com.baka3k.core.model.PagingInfo
import com.baka3k.core.network.datasource.MovieNetworkDataSource
import com.baka3k.core.network.model.NetworkChangeList
import com.baka3k.core.network.model.NetworkMovie
import com.baka3k.core.network.model.NetworkMovieResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * [MovieNetworkDataSource] implementation that provides static news resources to aid development
 */
@SuppressLint("LongLogTag")
class FakeMovieNetworkDataSource @Inject constructor(
    @Dispatcher(HiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : MovieNetworkDataSource {
    override suspend fun getPopularMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return withContext(ioDispatcher) {
            val networkMovieResponse =
                networkJson.decodeFromString(FakeData.movieData) as NetworkMovieResponse
            Result.Success(networkMovieResponse.results)
        }
    }

    override suspend fun getTopRateMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return withContext(ioDispatcher) {
            val networkMovieResponse =
                networkJson.decodeFromString(FakeData.movieData) as NetworkMovieResponse
            Result.Success(networkMovieResponse.results)
        }
    }

    override suspend fun getUpCommingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return withContext(ioDispatcher) {
            val networkMovieResponse =
                networkJson.decodeFromString(FakeData.movieData) as NetworkMovieResponse
            Result.Success(networkMovieResponse.results)

        }
    }

    override suspend fun getNowPlayingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return withContext(ioDispatcher) {
            val networkMovieResponse =
                networkJson.decodeFromString(FakeData.movieData) as NetworkMovieResponse
            Result.Success(networkMovieResponse.results)
        }
    }

    override suspend fun findMovie(
        query: String,
        pagingInfo: PagingInfo
    ): Result<List<NetworkMovie>> {
        TODO("Not yet implemented")
    }
}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [NetworkChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}
