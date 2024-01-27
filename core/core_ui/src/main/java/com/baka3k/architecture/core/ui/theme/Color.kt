package com.baka3k.architecture.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt
val Purple10 = Color(0xFF36003D)
val Purple20 = Color(0xFF560A5E)
val Purple30 = Color(0xFF702776)
val Purple40 = Color(0xFF8C4190)
val Purple80 = Color(0xFFFFA8FF)
val Purple90 = Color(0xFFFFD5FC)
val Purple95 = Color(0xFFFFEBFB)
val PurpleGray30 = Color(0xFF4E444C)
val PurpleGray50 = Color(0xFF7F747C)
val PurpleGray60 = Color(0xFF998D96)
val PurpleGray80 = Color(0xFFD0C2CC)
val PurpleGray90 = Color(0xFFEDDEE8)
val colorTextHeaderHomeDark = Color(204, 204, 204)
val colorTextHeaderHomeLight = Color(36, 42, 56)

val colorBackgroundDark = Color(36, 42, 56)
val colorBackgroundLight = Color(250, 250, 250)

val colorBackgroundEditDark = Color(48, 54, 73)
val colorBackgroundEditDarkFocus = Color(68, 64, 23)

val colorBackgroundEditLight = Color(244, 244, 244)
val colorBackgroundEditLightFocus = Color(204, 204, 204)

val colorContentEditTextLight = Color(10, 10, 19)
val colorContentEditTextDark = Color(244, 244, 244)

val ShimmerColorShades = listOf(
    Color.LightGray.copy(0.9f),
    Color.LightGray.copy(0.2f),
    Color.LightGray.copy(0.9f)
)

internal fun Color.lighten(luminance: Float): Color {
    val hsl = FloatArray(3)
    ColorUtils.RGBToHSL(
        (red * 256).roundToInt(),
        (green * 256).roundToInt(),
        (blue * 256).roundToInt(),
        hsl
    )
    hsl[2] = luminance
    return Color(ColorUtils.HSLToColor(hsl))
}
