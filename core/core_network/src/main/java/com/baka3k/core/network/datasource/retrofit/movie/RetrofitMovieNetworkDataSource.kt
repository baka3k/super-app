package com.baka3k.core.network.datasource.retrofit.movie

import com.baka3k.core.common.logger.Logger
import com.baka3k.core.common.result.Result
import com.baka3k.core.model.PagingInfo
import com.baka3k.core.network.BuildConfig
import com.baka3k.core.network.datasource.CreditNetworkDataSource
import com.baka3k.core.network.datasource.GenreNetworkDataSource
import com.baka3k.core.network.datasource.MovieNetworkDataSource
import com.baka3k.core.network.datasource.PersonNetworkDataSource
import com.baka3k.core.network.datasource.ReviewNetworkDataSource
import com.baka3k.core.network.datasource.secure.SSlContextVerification
import com.baka3k.core.network.model.NetworkCreditsResponse
import com.baka3k.core.network.model.NetworkGenre
import com.baka3k.core.network.model.NetworkMovie
import com.baka3k.core.network.model.NetworkPersonResponse
import com.baka3k.core.network.model.NetworkReview
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitMovieNetworkDataSource @Inject constructor(
    networkJson: Json, private val sslContextVerification: SSlContextVerification
) : MovieNetworkDataSource, CreditNetworkDataSource, GenreNetworkDataSource,
    PersonNetworkDataSource, ReviewNetworkDataSource {
    private val networkApi = Retrofit.Builder().baseUrl(backend_movie_url).client(netWorkClient())
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build().create(RetrofitMovieNetworkApi::class.java)

    private fun netWorkClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .certificatePinner(CertificatePinner.Builder().apply {
//                for ((key, value) in sslContextVerification.pinnings) {
//                    add(value, key)
//                }
//            }.build())
//            .sslSocketFactory(
//                sslContextVerification.sslSocketFactory, sslContextVerification.trustManager
//            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }

    override suspend fun getPopularMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getPopularMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun getTopRateMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getTopRateMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun getUpCommingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getUpCommingMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun getNowPlayingMovie(pagingInfo: PagingInfo): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.getNowPlayingMovie(page = pagingInfo.page).results
        }
    }

    override suspend fun findMovie(
        query: String, pagingInfo: PagingInfo
    ): Result<List<NetworkMovie>> {
        return invokeCatching {
            networkApi.findMovieFromServer(page = pagingInfo.page, query = query).results
        }
    }

    override suspend fun getCredits(movieId: Long): Result<NetworkCreditsResponse> {
        return invokeCatching {
            networkApi.getCredits(movieId = movieId)
        }
    }

    override suspend fun getGenresMovie(): Result<List<NetworkGenre>> {
        return invokeCatching {
            networkApi.getGenres().genres
        }
    }

    override suspend fun getGenreTvList(): Result<List<NetworkGenre>> {
        return invokeCatching {
            networkApi.getGenreTvList().genres
        }
    }

    override suspend fun getPerson(personId: String): Result<NetworkPersonResponse> {
        return invokeCatching {
            networkApi.getPerson(personId = personId)
        }
    }

    override suspend fun getReview(movieId: Long): Result<List<NetworkReview>> {
        return invokeCatching {
            networkApi.getReview(movieId = movieId).results
        }
    }

    inline fun <R> invokeCatching(block: () -> R): Result<R> {
        return com.baka3k.core.common.result.runCatching {
            block.invoke()
        }
    }

    companion object {
        private const val backend_movie_url = "${BuildConfig.BACKEND_MOVIE_URL}/3/movie/"
    }
}