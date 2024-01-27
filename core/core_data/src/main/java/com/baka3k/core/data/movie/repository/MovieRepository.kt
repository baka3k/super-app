package com.baka3k.core.data.movie.repository

import com.baka3k.core.common.result.Result
import com.baka3k.core.model.Genre
import com.baka3k.core.model.Movie
import com.baka3k.core.model.PagingInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovieStream(): Flow<List<Movie>>
    fun getNowPlayingMovieStream(): Flow<List<Movie>>
    fun getTopRateMovieStream(): Flow<List<Movie>>
    fun getUpCommingMovieStream(): Flow<List<Movie>>
    fun getMovieStream(movieId: Long): Flow<Movie>
    fun getGenreStream(movieId: Long): Flow<List<Genre>>
    fun getTempMovieStream(): Flow<List<Movie>>

    suspend fun loadMorePopularFromServer(pagingConfig: PagingInfo): Result<List<Movie>>
    suspend fun loadMoreNowPlayingFromServer(pagingConfig: PagingInfo): Result<List<Movie>>
    suspend fun loadMoreTopRateFromServer(pagingConfig: PagingInfo): Result<List<Movie>>
    suspend fun loadMoreUpComingFromServer(pagingConfig: PagingInfo): Result<List<Movie>>
    suspend fun findMovieFromServer(query: String, pagingConfig: PagingInfo): Result<List<Movie>>
}