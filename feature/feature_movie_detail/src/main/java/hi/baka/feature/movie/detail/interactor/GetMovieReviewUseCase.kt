package hi.baka.feature.movie.detail.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCaseParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.asResult
import com.baka3k.core.data.movie.repository.ReviewRepository
import com.baka3k.core.model.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) : UpStreamSingleUseCaseParameter<Long, Flow<Result<List<Review>>>> {
    override fun invoke(movieId: Long): Flow<Result<List<Review>>> {
        return reviewRepository.getMovieReviewsStream(movieId = movieId).asResult()
    }

    suspend fun pullReviewFromNetwork(movieId: Long): Result<Int> =
        reviewRepository.pullMovieReviewsFromNetwork(movieId = movieId)
}