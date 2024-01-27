package hi.baka.feature.movie.detail.interactor

import com.baka3k.core.common.interactor.SingleUseCaseWithParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.movie.repository.CreditRepository
import javax.inject.Inject

class GetCreditUseCase @Inject constructor(
    private val creditRepository: CreditRepository
) : SingleUseCaseWithParameter<Long, Result<Int>> {
    override suspend fun invoke(movieId: Long): Result<Int> {
        return creditRepository.getCredit(movieId = movieId)
    }
}