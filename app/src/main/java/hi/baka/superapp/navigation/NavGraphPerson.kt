package hi.baka.superapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.baka3k.core.navigation.Screen
import hi.baka.feature.movie.detail.navigation.MovieDetailDestination
import hi.baka.splitinstall.LoadFeature


fun NavGraphBuilder.personScreenComposeGraph(
    context: Context,
    onDismiss: () -> Unit,
    onBackPress: () -> Unit,
) {
    dialog(PersonScreenDestination.startScreen) {
        LoadFeature(
            context = context,
            featureName = FeaturePerson,
            onDismiss = onDismiss,
        ) {
            // Workaround to be able to use Dynamic Feature with Compose
            // https://issuetracker.google.com/issues/183677219

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(PersonScreenDestination.deepLinkUrl)
                `package` = context.packageName
            }
            context.startActivity(intent)
        }
    }
}

private const val FeaturePerson = "feature_person"

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