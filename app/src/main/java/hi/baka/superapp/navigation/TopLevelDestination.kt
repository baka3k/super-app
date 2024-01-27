package hi.baka.superapp.navigation

import com.baka3k.architecture.core.ui.theme.Icon
import com.baka3k.core.navigation.Screen

data class TopLevelDestination(
    override val startScreen: String,
    override val destinationScreen: String,
    override val deepLinkUrl: String,
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int
) :Screen {
}