package hi.baka.superapp.feature.movie.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import hi.baka.superapp.feature.movie.person.interactor.GetPersonUseCase
import hi.baka.superapp.feature.movie.person.navigation.PersonScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

//@HiltViewModel
//class PersonViewModel  @Inject constructor(
class PersonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPersonUseCase: GetPersonUseCase,
    @Dispatcher(HiDispatchers.SERIAL) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _personId: String = checkNotNull(
        savedStateHandle[PersonScreenDestination.personIdArg]
    )
    private val personStream = getPersonUseCase.invoke(_personId)
    val personUiIState: StateFlow<PersonScreenUiIState> = personStream.map {
        when (it) {
            is com.baka3k.core.common.result.Result.Loading -> {
                PersonScreenUiIState.Loading
            }

            is com.baka3k.core.common.result.Result.Error -> {
                PersonScreenUiIState.Error
            }

            is com.baka3k.core.common.result.Result.Success -> {
                PersonScreenUiIState.Success(it.data)
            }

            else -> {
                PersonScreenUiIState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PersonScreenUiIState.Loading
    )
}