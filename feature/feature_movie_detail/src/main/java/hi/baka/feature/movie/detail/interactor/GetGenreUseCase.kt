package hi.baka.feature.movie.detail.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCaseParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.asResult
import com.baka3k.core.data.movie.repository.GenreRepository
import com.baka3k.core.model.Genre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val genreRepository: GenreRepository
) : UpStreamSingleUseCaseParameter<Long, Flow<Result<List<Genre>>>> {
    override fun invoke(idMovie: Long): Flow<Result<List<Genre>>> {
        return genreRepository.getGenres(idMovie).asResult()
    }
}