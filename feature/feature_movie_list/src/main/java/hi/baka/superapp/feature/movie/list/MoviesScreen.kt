package hi.baka.superapp.feature.movie.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baka3k.architecture.core.ui.component.SearchEditText
import  hi.baka.superapp.feature.movie.list.ui.nowPlayingMovie
import  hi.baka.superapp.feature.movie.list.ui.popularMovieView
import  hi.baka.superapp.feature.movie.list.ui.upComingMovieView
import com.baka3k.core.common.logger.Logger


@Composable
fun MovieRoute(
    navigateToMovieDetail: (Long) -> Unit,
    navigateToSearchMovie: (String) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiNowPlayingUiState by viewModel.nowPlayingUiState.collectAsStateWithLifecycle()
    val uiPopularUiState by viewModel.popularUiState.collectAsStateWithLifecycle()
    val uiUpComingUiState by viewModel.upComingUiState.collectAsStateWithLifecycle()
    MoviesScreen(
        navigateToMovieDetail = navigateToMovieDetail,
        navigateToSearchMovie = navigateToSearchMovie,
        nowPlayingUiState = uiNowPlayingUiState,
        popularUiState = uiPopularUiState,
        uiUpComingUiState = uiUpComingUiState,
    )
}


@Composable
fun MoviesScreen(
    navigateToMovieDetail: (Long) -> Unit,
    navigateToSearchMovie: (String) -> Unit,
    nowPlayingUiState: NowPlayingUiState,
    popularUiState: PopularUiState,
    uiUpComingUiState: UpComingUiState,
) {
    val reusableModifier = Modifier.fillMaxWidth()
    var text by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        SearchEditText(
            modifier = reusableModifier,
            keyboardActions = KeyboardActions(onDone = {
                if (text.isNotEmpty()) {
                    navigateToSearchMovie(text)
                } else {
                    Logger.d("Empty input")
                }
            })
        ) { value ->
            text = value
        }
        nowPlayingMovie(
            navigateToMovieDetail = navigateToMovieDetail,
            nowPlayingUiState = nowPlayingUiState,
            modifier = reusableModifier
        )
        popularMovieView(
            navigateToMovieDetail = navigateToMovieDetail,
            popularUiState = popularUiState,
            modifier = reusableModifier
        )
        upComingMovieView(
            navigateToMovieDetail = navigateToMovieDetail,
            upComingUiState = uiUpComingUiState,
            modifier = reusableModifier
        )
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect {
            if (it) loadMore()
        }
    }
}