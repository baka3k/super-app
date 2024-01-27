package hi.baka.superapp.feature.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hi.baka.superapp.feature.movie.list.interactor.GetMovieUpComingUseCase
import com.baka3k.core.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import hi.baka.superapp.feature.movie.list.interactor.GetMovieNowPlayingUseCase
import hi.baka.superapp.feature.movie.list.interactor.GetMoviePopularUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviePopularUseCase: GetMoviePopularUseCase,
    private val movieNowPlayingUseCase: GetMovieNowPlayingUseCase,
    private val upComingUseCase: GetMovieUpComingUseCase,
) : ViewModel() {
    private val _isLoadingNowPlaying = MutableStateFlow(false)
    private val _isLoadingPopular = MutableStateFlow(false)
    private val _isLoadingUpComing = MutableStateFlow(false)
    private val nowPlayingStream = movieNowPlayingUseCase.invoke()
    private val popularStream = moviePopularUseCase.invoke()
    private val upCommingStream = upComingUseCase.invoke()
    val nowPlayingUiState =
        combine(nowPlayingStream, _isLoadingNowPlaying) { nowPlayingResult, isLoading ->
            if (isLoading) {
                NowPlayingUiState.Loading
            } else {
                val nowplayingState: NowPlayingUiState = when (nowPlayingResult) {
                    is Result.Success -> NowPlayingUiState.Success(nowPlayingResult.data)
                    is Result.Loading -> NowPlayingUiState.Loading
                    is Result.Error -> NowPlayingUiState.Error
                }
                nowplayingState
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NowPlayingUiState.Loading
        )
    val popularUiState =
        combine(popularStream, _isLoadingPopular) { popularMoviesResult, isLoading ->
            if (isLoading) {
                PopularUiState.Loading
            } else {
                val popularMovieState: PopularUiState = when (popularMoviesResult) {
                    is Result.Success -> {
                        PopularUiState.Success(popularMoviesResult.data)
                    }

                    is Result.Loading -> PopularUiState.Loading
                    is Result.Error -> PopularUiState.Error
                }
                popularMovieState
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PopularUiState.Loading
        )
    val upComingUiState =
        combine(upCommingStream, _isLoadingUpComing) { upComingResult, isLoading ->
            if (isLoading) {
                UpComingUiState.Loading
            } else {
                val upComingMovieState: UpComingUiState = when (upComingResult) {
                    is Result.Success -> {
                        UpComingUiState.Success(upComingResult.data)
                    }

                    is Result.Loading -> UpComingUiState.Loading
                    is Result.Error -> UpComingUiState.Error
                }
                upComingMovieState
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UpComingUiState.Loading
        )

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch {
                _isLoadingNowPlaying.value = true
                movieNowPlayingUseCase.loadMore()
                _isLoadingNowPlaying.value = false
            }
            launch {
                _isLoadingPopular.value = true
                moviePopularUseCase.loadMore()
                _isLoadingPopular.value = false
            }
            launch {
                _isLoadingUpComing.value = true
                upComingUseCase.loadMore()
                _isLoadingUpComing.value = false
            }
        }
    }
}