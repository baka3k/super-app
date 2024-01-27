package hi.baka.feature.movie.detail.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCaseParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.common.result.asResult
import com.baka3k.core.data.movie.repository.CreditRepository
import com.baka3k.core.model.Crew
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCrewUseCase @Inject constructor(
    private val creditRepository: CreditRepository
) : UpStreamSingleUseCaseParameter<Long, Flow<Result<List<Crew>>>> {
    override fun invoke(idMovie: Long): Flow<Result<List<Crew>>> {
        return creditRepository.getCrewStream(idMovie).asResult()
    }
}