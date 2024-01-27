package hi.baka.feature.movie.detail.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.baka3k.core.navigation.Screen
import hi.baka.feature.movie.detail.MovieDetailRouter

object MovieDetailDestination : Screen {
    const val movieIdArg = "movieId"
    override val startScreen: String
        get() = "movie_detail_router/{$movieIdArg}"
    override val destinationScreen: String
        get() = "movie_detail_router_next_screen"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.test.feature.moviedetail.router/moviedetailScreen"

    fun createNavigationRoute(movieIdArg: String): String {
        val movieId = Uri.encode(movieIdArg)
        return "movie_detail_router/$movieId"
    }

    fun createNavigationRouteByDeepLink(movieId: Long): Uri {
        return "$deepLinkUrl?$movieIdArg=$movieId".toUri()
    }
}

fun NavGraphBuilder.movieDetailComposeGraph(
    navigateToPersonScreen: (Long) -> Unit,
    onBackPress: () -> Unit
) {
    composable(route = MovieDetailDestination.startScreen,
        arguments = listOf(navArgument(MovieDetailDestination.movieIdArg) {
            type = NavType.StringType
        }),
        deepLinks = listOf(navDeepLink {
            uriPattern =
                "${MovieDetailDestination.deepLinkUrl}?${MovieDetailDestination.movieIdArg}={${MovieDetailDestination.movieIdArg}}"
        })
    ) {
        MovieDetailRouter(
            navigateToPersonScreen = navigateToPersonScreen,
            onBackPress = onBackPress
        )
    }
}