package hi.baka.superapp.feature.movie.person

import com.baka3k.core.model.Person

sealed interface PersonScreenUiIState {
    data class Success(val person: Person) : PersonScreenUiIState
    object Error : PersonScreenUiIState
    object Loading : PersonScreenUiIState
}
