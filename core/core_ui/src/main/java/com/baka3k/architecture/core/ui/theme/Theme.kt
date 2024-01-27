package com.baka3k.architecture.core.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorPalette(
    val colorBgEditText: Color,
    val colorBgEditTextFocus: Color,
    val colorContentEditText: Color,
    val colorBackgroundTheme: Color,
    val colorTextHeaderHome: Color,
)

val colorPaletteDark = AppColorPalette(
    colorBgEditText = colorBackgroundEditDark,
    colorBgEditTextFocus = colorBackgroundEditDarkFocus,
    colorContentEditText = colorContentEditTextDark,
    colorBackgroundTheme = colorBackgroundDark,
    colorTextHeaderHome = colorTextHeaderHomeDark,
)
val colorPaletteLight = AppColorPalette(
    colorBgEditText = colorBackgroundEditLight,
    colorBgEditTextFocus = colorBackgroundEditLightFocus,
    colorContentEditText = colorContentEditTextLight,
    colorBackgroundTheme = colorBackgroundLight,
    colorTextHeaderHome = colorTextHeaderHomeLight
)
val lightAndroidBackgroundTheme = BackgroundTheme(color = colorBackgroundLight)
val darkAndroidBackgroundTheme = BackgroundTheme(color = colorBackgroundDark)

object AppTheme {
    val colors: AppColorPalette
        @Composable get() = provideColors.current
}

val provideColors = compositionLocalOf {
    colorPaletteDark // default
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        colorPaletteDark
    } else {
        colorPaletteLight
    }
    val backgroundTheme = when {
        darkTheme -> darkAndroidBackgroundTheme
        else -> lightAndroidBackgroundTheme
    }

    CompositionLocalProvider(
        provideColors provides colors,
        LocalBackgroundTheme provides backgroundTheme
    ) {
        MaterialTheme(
            typography = HiTypography,
            shapes = Shapes,
            content = content
        )
    }
}