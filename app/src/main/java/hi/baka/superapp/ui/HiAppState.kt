package hi.baka.superapp.ui

import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Stream
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baka3k.architecture.core.ui.theme.Icon
import com.baka3k.core.navigation.Screen
import hi.baka.superapp.R
import hi.baka.superapp.feature.movie.list.navigation.MovieDestination
import hi.baka.superapp.navigation.TopLevelDestination

@Composable
fun rememberHiAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): HiAppState {
    return remember(navController, windowSizeClass) {
        HiAppState(navController, windowSizeClass)
    }
}

@Stable
class HiAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            startScreen = MovieDestination.startScreen,
            destinationScreen = MovieDestination.destinationScreen,
            deepLinkUrl = MovieDestination.deepLinkUrl,
            selectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Rounded.Home),
            unselectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Default.Home),
            iconTextId = R.string.home
        ),
        TopLevelDestination(
            startScreen = MovieDestination.startScreen,
            destinationScreen = MovieDestination.destinationScreen,
            deepLinkUrl = MovieDestination.deepLinkUrl,
            selectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Rounded.Stream),
            unselectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Default.Stream),
            iconTextId = R.string.streams
        ),
        TopLevelDestination(
            startScreen = MovieDestination.startScreen,
            destinationScreen = MovieDestination.destinationScreen,
            deepLinkUrl = MovieDestination.deepLinkUrl,
            selectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Rounded.Message),
            unselectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Default.Message),
            iconTextId = R.string.message
        ),
        TopLevelDestination(
            startScreen = MovieDestination.startScreen,
            destinationScreen = MovieDestination.destinationScreen,
            deepLinkUrl = MovieDestination.deepLinkUrl,
            selectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Rounded.AccountCircle),
            unselectedIcon = Icon.ImageVectorIcon(imageVector = Icons.Default.AccountCircle),
            iconTextId = R.string.profile
        ),
    )


    fun navigate(destination: Screen, route: String? = null) {
//        Log.d("HAHA","destination: $destination route:$route")
        trace("Navigation: $destination") {
            if (destination is TopLevelDestination) {
                navController.navigate(route ?: destination.startScreen) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            } else {
                navController.navigate(route ?: destination.startScreen)
            }
        }
    }

    fun navigate( deepLink: Uri) {
        trace("Navigation: $deepLink") {
//            if (destination is TopLevelDestination) {
//                navController.navigate(deepLink = deepLink)
//            } else {
//                navController.navigate(deepLink)
//            }
            navController.navigate(deepLink)
        }
    }

    fun onBackPress() {
        navController.popBackStack()
    }
}

