package hi.baka.superapp.feature.movie.list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.baka3k.core.navigation.Screen
import hi.baka.superapp.feature.movie.list.MovieRoute

object MovieDestination : Screen {
    override val startScreen: String
        get() = "movie_router"
    override val destinationScreen: String
        get() = "movie_destination"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.test.feature.movie.router/MoviesScreen"
}

fun NavGraphBuilder.movieScreenComposeGraph(
    navigateToMovieDetail: (Long) -> Unit,
    navigateToSearchMovie: (String) -> Unit,
) {
    composable(
        route = MovieDestination.startScreen
    ) {
        MovieRoute(
            navigateToMovieDetail = navigateToMovieDetail,
            navigateToSearchMovie = navigateToSearchMovie
        )
    }
}
