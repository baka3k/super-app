package hi.baka.feature.compose.person.interactor

import com.baka3k.core.common.interactor.UpStreamSingleUseCaseParameter
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.movie.repository.PersonRepository
import com.baka3k.core.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(
    private val personRepository: PersonRepository
) : UpStreamSingleUseCaseParameter<String, Flow<Result<Person>>> {
    override fun invoke(personId: String): Flow<Result<Person>> =
        personRepository.getPerson(personId)
}