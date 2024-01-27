package hi.baka.superapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.baka3k.core.navigation.Screen
import hi.baka.feature.movie.detail.navigation.MovieDetailDestination
import hi.baka.feature.movie.detail.navigation.movieDetailComposeGraph
import hi.baka.splitinstall.LoadFeature
import hi.baka.superapp.feature.movie.list.navigation.MovieDestination
import hi.baka.superapp.feature.movie.list.navigation.movieScreenComposeGraph

@Composable
fun HiNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToScreen: (Screen, String) -> Unit,
    startScreen: String = MovieDestination.startScreen,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = startScreen, modifier = modifier)
    {
        movieScreenComposeGraph(
            navigateToMovieDetail = {
                onNavigateToScreen(
                    MovieDetailDestination,
                    MovieDetailDestination.createNavigationRoute(it.toString())
                )
            },
            navigateToSearchMovie = {
//                onNavigateToScreen(
//                    SearchMovieDestination,
//                    SearchMovieDestination.createNavigationRoute(it)
//                )
            }
        )
        movieDetailComposeGraph(onBackPress = onBackPress, navigateToPersonScreen = { personId ->
//            onNavigateToScreen(
//                PersonScreenDestination,
//                PersonScreenDestination.createNavigationRoute(personId.toString())
//            )
            onNavigateToScreen(
                ReactScreenDestination,
                ReactScreenDestination.createNavigationRoute(personId.toString())
            )

//            onNavigateToScreen(
//                ReactScreenDestination,
//                ReactScreenDestination.createNavigationRoute(personId.toString())
//            )
        })
        personScreenComposeGraph(context = context, onBackPress = onBackPress, onDismiss = {})
        reactRuntimeGraph(context = context, onBackPress = onBackPress, onDismiss = {})
    }
}
