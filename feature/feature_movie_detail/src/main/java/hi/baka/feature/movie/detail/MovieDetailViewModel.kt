package hi.baka.feature.movie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baka3k.core.common.Dispatcher
import com.baka3k.core.common.HiDispatchers
import com.baka3k.core.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import hi.baka.feature.movie.detail.interactor.GetCastUseCase
import hi.baka.feature.movie.detail.interactor.GetCreditUseCase
import hi.baka.feature.movie.detail.interactor.GetCrewUseCase
import hi.baka.feature.movie.detail.interactor.GetGenreUseCase
import hi.baka.feature.movie.detail.interactor.GetMovieDetailUseCase
import hi.baka.feature.movie.detail.interactor.GetMovieReviewUseCase
import hi.baka.feature.movie.detail.navigation.MovieDetailDestination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    movieDetailUseCase: GetMovieDetailUseCase,
    crewUseCase: GetCrewUseCase,
    castUseCase: GetCastUseCase,
    getGenreUseCase: GetGenreUseCase,
    private val getMovieReviewUseCase: GetMovieReviewUseCase,
    private val getCreditUseCase: GetCreditUseCase,
    @Dispatcher(HiDispatchers.SERIAL) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _movieId: String = checkNotNull(
        savedStateHandle[MovieDetailDestination.movieIdArg]
    )
    private val movieId = _movieId.toLong()
    private val movieDetailStream = movieDetailUseCase.invoke(movieId)
    private val genreStream = getGenreUseCase.invoke(movieId)
    private val castStream = castUseCase(movieId)
    private val crewStream = crewUseCase.invoke(movieId)
    private val reviewStream = getMovieReviewUseCase.invoke(movieId)
    private val creditStream = pullCreditFromNetwork(movieId)
    val creditUiState: StateFlow<CreditUiState> = combine(
        creditStream, castStream, crewStream
    ) { creditResult, castResult, crewResult ->
        val creditUiState = when (creditResult) {
            is Result.Loading -> {
                CreditUiState(crewUiState = CrewUiState.Loading, castUiState = CastUiState.Loading)
            }
            is Result.Error -> {
                CreditUiState(crewUiState = CrewUiState.Error, castUiState = CastUiState.Error)
            }
            else -> {
                val castData = if (castResult is Result.Success) {
                    castResult.data
                } else {
                    emptyList()
                }
                val crewData = if (crewResult is Result.Success) {
                    crewResult.data
                } else {
                    emptyList()
                }
                CreditUiState(
                    crewUiState = CrewUiState.Success(crewData),
                    castUiState = CastUiState.Success(castData)
                )
            }
        }
        creditUiState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CreditUiState(
            crewUiState = CrewUiState.Loading, castUiState = CastUiState.Loading
        )
    )
    val movieDetailUiState =
        combine(
            movieDetailStream,
            genreStream,
            pullReviewFromNetwork(movieId),
            reviewStream
        ) { movieDetailResult, genreResult, reviewNetworkResult, reviewDBResult ->
            val genreList = if (genreResult is Result.Success) {
                genreResult.data
            } else {
                emptyList()
            }
            val reviews = if (reviewDBResult is Result.Success) {
                reviewDBResult.data
            } else {
                emptyList()
            }
            when (movieDetailResult) {
                is Result.Success -> {
                    val data = movieDetailResult.data
                    data.genres.addAll(genreList)
                    MovieDetailUiState.Success(data, reviews)
                }
                is Result.Loading -> {
                    MovieDetailUiState.Loading
                }
                else -> {
                    MovieDetailUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailUiState.Loading
        )

    private fun pullCreditFromNetwork(movieId: Long): Flow<Result<Int>> {
        return flow {
            val data = getCreditUseCase.invoke(movieId)
            emit(data)
        }.flowOn(ioDispatcher)
    }

    private fun pullReviewFromNetwork(movieId: Long): Flow<Result<Int>> {
        return flow {
            val data = getMovieReviewUseCase.pullReviewFromNetwork(movieId)
            emit(data)
        }.flowOn(ioDispatcher)
    }
}