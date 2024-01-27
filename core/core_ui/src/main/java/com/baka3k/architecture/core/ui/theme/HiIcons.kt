package com.baka3k.architecture.core.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
