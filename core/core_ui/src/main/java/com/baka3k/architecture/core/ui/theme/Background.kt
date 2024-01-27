package com.baka3k.architecture.core.ui.theme


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A class to model background values for App in Android,
 * including color, tonal elevation and gradient colors.
 */
@Immutable
data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
    val primaryGradientColor: Color = Color.Unspecified,
    val secondaryGradientColor: Color = Color.Unspecified,
    val tertiaryGradientColor: Color = Color.Unspecified,
    val neutralGradientColor: Color = Color.Unspecified
)

/**
 * A composition local for [BackgroundTheme].
 */
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }