package hi.baka.feature.movie.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.baka3k.architecture.core.ui.component.HiTopAppBar
import com.baka3k.architecture.core.ui.component.ShimmerAnimation
import com.baka3k.architecture.core.ui.theme.AppTheme
import hi.baka.feature.movie.detail.ui.CastScreen
import hi.baka.feature.movie.detail.ui.CrewScreen
import hi.baka.feature.movie.detail.ui.ReviewScreen
import hi.baka.feature.movie.detail.ui.Title

@Composable
fun MovieDetailRouter(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateToPersonScreen: (Long) -> Unit,
    onBackPress: () -> Unit
) {
    val movieDetailUiState by viewModel.movieDetailUiState.collectAsStateWithLifecycle()
    val creditUiState by viewModel.creditUiState.collectAsStateWithLifecycle()
    MovieDetailScreen(
        movieDetailUiState = movieDetailUiState,
        creditUiState = creditUiState,
        navigateToPersonScreen = navigateToPersonScreen,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieDetailUiState: MovieDetailUiState, creditUiState: CreditUiState,
    navigateToPersonScreen: (Long) -> Unit,
    onBackPress: () -> Unit
) {
    val reusableModifier = Modifier.fillMaxWidth()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Box(modifier = reusableModifier)
    {
        Column(
            modifier = reusableModifier
                .background(AppTheme.colors.colorBackgroundTheme)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
        ) {
            MovieInfoScreen(movieDetailUiState = movieDetailUiState, modifier = reusableModifier)
            Title(text = "Cast", modifier = reusableModifier)
            CastScreen(
                castUiState = creditUiState.castUiState,
                modifier = reusableModifier,
                onItemCastClicked = navigateToPersonScreen
            )
            Title(text = "Crew", modifier = reusableModifier)
            CrewScreen(
                crewUiState = creditUiState.crewUiState,
                modifier = reusableModifier,
                onItemCrewClicked = navigateToPersonScreen
            )
            ReviewScreen(movieDetailUiState = movieDetailUiState, modifier = reusableModifier)
        }
        HiTopAppBar(
            title = "",
            onBackPress = onBackPress,
            modifier = Modifier.wrapContentHeight(),
            scrollBehavior = scrollBehavior
        )
    }
}


@Composable
private fun MovieInfoScreen(
    movieDetailUiState: MovieDetailUiState,
    modifier: Modifier
) {
    when (movieDetailUiState) {
        MovieDetailUiState.Loading -> {
            ShimmerAnimation()
        }
        MovieDetailUiState.Error -> {
            ShimmerAnimation()
        }
        is MovieDetailUiState.Success -> {
            val movie = movieDetailUiState.movie
            hi.baka.feature.movie.detail.ui.MovieInfoScreen(movie = movie, modifier = modifier)
        }
    }
}