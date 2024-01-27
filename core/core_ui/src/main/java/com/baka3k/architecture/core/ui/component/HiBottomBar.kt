package com.baka3k.architecture.core.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HiNavigationBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = NavigationDefaults.NavigationContainerColor,
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun NavigationBottomBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClicked: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClicked,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = NavigationDefaults.navigationContentColor(),
            selectedTextColor = NavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = NavigationDefaults.navigationContentColor(),
            indicatorColor = NavigationDefaults.navigationIndicatorColor()
        )
    )
}

object NavigationDefaults {
    val NavigationContainerColor = Color.Transparent

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
