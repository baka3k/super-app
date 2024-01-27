package hi.baka.superapp.feature.movie.person.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.baka3k.core.navigation.Screen
import hi.baka.superapp.feature.movie.person.PersonScreenRouter

object PersonScreenDestination : Screen {
    const val personIdArg = "personId"
    override val startScreen: String
        get() = "person_view_router/{$personIdArg}"
    override val destinationScreen: String
        get() = "person_view_router_next_screen"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.test.movie.person.router/personview"

    fun createNavigationRoute(personIdArg: String): String {
        val personID = Uri.encode(personIdArg)
        return "person_view_router/$personID"
    }

    fun createNavigationRouteByDeepLink(personId: Long): Uri {
        return "$deepLinkUrl?$personIdArg=$personId".toUri()
    }
}

fun NavGraphBuilder.personScreenComposeGraph(
    onBackPress: () -> Unit
) {
    composable(
        route = PersonScreenDestination.startScreen,
        arguments = listOf(
            navArgument(PersonScreenDestination.personIdArg) { type = NavType.StringType }
        ),
        deepLinks = listOf(navDeepLink {
            uriPattern =
                "${PersonScreenDestination.deepLinkUrl}?${PersonScreenDestination.personIdArg}={${PersonScreenDestination.personIdArg}}"
        })
    ) {
        PersonScreenRouter(onBackPress = onBackPress)
    }
}