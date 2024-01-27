package com.baka3k.architecture.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.baka3k.architecture.core.ui.R

@Composable
fun AsyncImageView(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes placeholderDrawableResId: Int = R.drawable.placeholder,
    @DrawableRes errorDrawableResId: Int = R.drawable.placeholder,
    data: Any?,
    colorFilter: ColorFilter? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .placeholder(placeholderDrawableResId)
            .diskCachePolicy(CachePolicy.ENABLED)
            .error(errorDrawableResId)
            .data(data)
            .build(),
        contentDescription = "",
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}