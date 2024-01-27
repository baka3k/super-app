package hi.baka.superapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.baka3k.architecture.core.ui.component.HiBackground
import com.baka3k.architecture.core.ui.component.HiNavigationBottomBar
import com.baka3k.architecture.core.ui.component.NavigationBottomBarItem
import com.baka3k.architecture.core.ui.theme.AppTheme
import com.baka3k.architecture.core.ui.theme.Icon
import hi.baka.superapp.navigation.HiNavGraph
import hi.baka.superapp.navigation.TopLevelDestination

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun HiComposeApp(
    windowSizeClass: WindowSizeClass,
    appState: HiAppState = rememberHiAppState(windowSizeClass)
) {
    AppTheme(
        darkTheme = ThemeState.isDarkTheme,
    ) {
        HiBackground {
            val navController = appState.navController
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        testTagsAsResourceId = true
                    },
                containerColor = Color.Transparent,
                contentColor = AppTheme.colors.colorBackgroundTheme,
                bottomBar = {
                    AppBottomBar(appState)
                },
            ) { padding ->
                Column {
                    HiNavGraph(
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .consumedWindowInsets(padding),
                        onNavigateToScreen = appState::navigate,
                        onBackPress = appState::onBackPress
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomBar(appState: HiAppState) {
    BottomBarNavigation(
        destinations = appState.topLevelDestinations,
        onNavigateToDestination = appState::navigate,
        currentDestination = appState.currentDestination
    )
}

@Composable
fun BottomBarNavigation(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    HiNavigationBottomBar(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
    ) {
        destinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.startScreen } == true
            NavigationBottomBarItem(
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null,
                            tint = AppTheme.colors.colorContentEditText
                        )
                        is Icon.DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null,
                            tint = AppTheme.colors.colorContentEditText
                        )
                    }
                },
                label = {
                    Text(
                        stringResource(destination.iconTextId),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = AppTheme.colors.colorContentEditText
                        )
                    )
                },
                onClicked = { onNavigateToDestination(destination) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

object ThemeState {
    var isDarkTheme by mutableStateOf(true)
}
