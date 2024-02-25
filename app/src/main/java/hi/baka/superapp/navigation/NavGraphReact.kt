package hi.baka.superapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.baka3k.core.navigation.Screen
import hi.baka.splitinstall.LoadFeature


fun NavGraphBuilder.reactRuntimeGraph(
    context: Context,
    actions: Actions,
    onBackPress: () -> Unit,
) {
    dialog(
        route = ReactScreenDestination.startScreen,
//        arguments = listOf(navArgument(ReactScreenDestination.screenIdArg) {
//            type = NavType.StringType
//        }),
//        deepLinks = listOf(navDeepLink {
//            uriPattern =
//                "${ReactScreenDestination.deepLinkUrl}?${ReactScreenDestination.screenIdArg}={${ReactScreenDestination.screenIdArg}}"
//        })
    ) {
        LoadFeature(
            context = context,
            featureName = FeatureReact,
            onDismiss = actions.navigateUp, //
        ) {
            // Workaround to be able to use Dynamic Feature with Compose
            val abc = Uri.parse(ReactScreenDestination.deepLinkUrl)
            // https://issuetracker.google.com/issues/183677219
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(ReactScreenDestination.deepLinkUrl)
                `package` = context.packageName
            }
            context.startActivity(intent)
        }
    }
}

private const val FeatureReact = "feature_react"

object ReactScreenDestination : Screen {
    const val screenIdArg = "screenId"
    override val startScreen: String
        get() = "react_view_router/{$screenIdArg}"
    override val destinationScreen: String
        get() = "react_view_router_next_screen"
    override val deepLinkUrl: String
        get() = "android-app://com.baka3k.test.movie.react.router/reactview"

    fun createNavigationRoute(personIdArg: String): String {
        val personID = Uri.encode(personIdArg)
        return "react_view_router/$personID"
    }

    fun createNavigationRouteByDeepLink(screenID: Long): Uri {
        return "$deepLinkUrl?$screenIdArg=$screenID".toUri()
    }
}