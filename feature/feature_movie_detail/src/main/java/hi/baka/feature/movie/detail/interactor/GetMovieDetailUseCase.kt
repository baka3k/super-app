package hi.baka.feature.movie.detail.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCaseParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.asResult
import com.baka3k.core.data.movie.repository.MovieRepository
import com.baka3k.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UpStreamSingleUseCaseParameter<Long, Flow<Result<Movie>>> {
    override fun invoke(movieId: Long): Flow<Result<Movie>> {
        return movieRepository.getMovieStream(movieId).asResult()
    }
}