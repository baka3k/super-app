package hi.baka.feature.movie.detail

import com.baka3k.core.model.Cast
import com.baka3k.core.model.Crew
import com.baka3k.core.model.Movie
import com.baka3k.core.model.Review

sealed interface MovieDetailUiState {
    data class Success(val movie: Movie,val reviews: List<Review>) : MovieDetailUiState
    object Error : MovieDetailUiState
    object Loading : MovieDetailUiState
}
sealed interface CastUiState {
    data class Success(val casts: List<Cast>) : CastUiState
    object Error : CastUiState
    object Loading : CastUiState
}

sealed interface CrewUiState {
    data class Success(val crews: List<Crew>) : CrewUiState
    object Error : CrewUiState
    object Loading : CrewUiState
}

data class CreditUiState(
    val crewUiState: CrewUiState,
    val castUiState: CastUiState
)