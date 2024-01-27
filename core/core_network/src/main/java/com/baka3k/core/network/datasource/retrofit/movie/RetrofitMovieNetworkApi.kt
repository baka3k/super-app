package com.baka3k.core.network.datasource.retrofit.movie


import com.baka3k.core.network.BuildConfig
import com.baka3k.core.network.model.NetworkCreditsResponse
import com.baka3k.core.network.model.NetworkGenreResponse
import com.baka3k.core.network.model.NetworkMovieResponse
import com.baka3k.core.network.model.NetworkPersonResponse
import com.baka3k.core.network.model.NetworkReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMovieNetworkApi {
    @GET(value = "popular")
    suspend fun getPopularMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
//    ): NetworkResponse<NetworkMovieResponse>

    @GET(value = "top_rated")
    suspend fun getTopRateMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse

    @GET(value = "upcoming")
    suspend fun getUpCommingMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
//    ): NetworkResponse<NetworkMovieResponse>


    @GET(value = "now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse

    @GET("/3/movie/{movieID}/credits")
    suspend fun getCredits(
        @Path("movieID") movieId: Long,
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkCreditsResponse

    @GET("3/genre/tv/list")
    suspend fun getGenreTvList(
        @Query("language") lang: String = "en-US",
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkGenreResponse

    @GET("/3/genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkGenreResponse

    @GET("/3/person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") personId: String = "",
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkPersonResponse

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getReview(
        @Path("movie_id") movieId: Long = 0,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkReviewResponse

    @GET("/3/search/movie")
    suspend fun findMovieFromServer(
        @Query("query") query: String = "",
        @Query("page") page: Int = 1,
        @Query("api_key") clientId: String = BuildConfig.MOVIEDB_ACCESS_KEY
    ): NetworkMovieResponse
    
    
}