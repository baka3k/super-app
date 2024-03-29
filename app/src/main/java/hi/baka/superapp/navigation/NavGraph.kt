package hi.baka.superapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.baka3k.core.navigation.Screen
import hi.baka.feature.movie.detail.navigation.MovieDetailDestination
import hi.baka.feature.movie.detail.navigation.movieDetailComposeGraph
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
    val actions = remember(navController) { Actions(navController, context) }
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
            onNavigateToScreen(
                ReactScreenDestination,
                ReactScreenDestination.createNavigationRoute(personId.toString())
            )
//            actions.navigateToPerson(personId)
        })
        reactRuntimeGraph(
            context = context,
            onBackPress = onBackPress,
            actions = actions
        )
        personScreenComposeGraph(context = context, onBackPress = onBackPress, onDismiss = {})
    }
}

data class Actions(val navController: NavHostController, val context: Context) {
    val navigateToPerson: (Long) -> Unit = { personId ->
        navController.navigate(ReactScreenDestination.createNavigationRoute(personId.toString()))
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}