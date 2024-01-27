package com.baka3k.core.data.movie.repository.real

import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.Result.Error
import com.baka3k.core.common.result.Result.Success
import com.baka3k.core.data.movie.model.asEntity
import com.baka3k.core.data.movie.repository.ReviewRepository
import com.baka3k.core.database.dao.ReviewDao
import com.baka3k.core.database.model.asExternalModel
//import com.baka3k.core.datastore.HiPreferencesDataSource
import com.baka3k.core.model.Review
import com.baka3k.core.network.datasource.ReviewNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDao: ReviewDao,
    private val network: ReviewNetworkDataSource,
//    private val preference: HiPreferencesDataSource,
    @Dispatcher(HiDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ReviewRepository {
    override suspend fun pullMovieReviewsFromNetwork(movieId: Long): Result<Int> =
        withContext(ioDispatcher) {

            when (val networkResponse = network.getReview(movieId)) {
                is Success -> {
                    val networkData = networkResponse.data
                    reviewDao.insertOrIgnore(networkData.map {
                        it.asEntity(movieId = movieId)
                    })
                    Success(networkData.size)
                }

                is Error -> {
                    Error(Exception(""))
                }

                else -> {
                    Success(0)
                }
            }
        }

    override fun getMovieReviewsStream(movieId: Long): Flow<List<Review>> {
        return reviewDao.getMovieReviews(movieId).map { data ->
            data.map { it.asExternalModel() }
        }.flowOn(ioDispatcher)
    }

    companion object {
        private const val TAG = "ReviewRepositoryImpl"
    }
}