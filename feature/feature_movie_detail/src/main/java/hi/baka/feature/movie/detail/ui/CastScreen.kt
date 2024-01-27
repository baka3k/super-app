package hi.baka.feature.movie.detail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.baka3k.architecture.core.ui.component.AsyncImageView
import com.baka3k.architecture.core.ui.component.ShimmerAnimation
import com.baka3k.architecture.core.ui.theme.AppTheme
import com.baka3k.core.common.logger.Logger
import com.baka3k.core.data.movie.model.PhotoSize
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.model.Cast
import hi.baka.feature.movie.detail.CastUiState


@Composable
fun CastScreen(
    castUiState: CastUiState,
    modifier: Modifier = Modifier,
    onItemCastClicked: (Long) -> Unit
) {
    when (castUiState) {
        is CastUiState.Loading -> {
            ShimmerAnimation()
        }
        is CastUiState.Error -> {
            Logger.d("test", " castUiState Err")
        }
        is CastUiState.Success -> {
            CastUi(
                casts = castUiState.casts.filter { it.profilePath.isNotEmpty() },
                modifier = modifier,
                onItemCastClicked = onItemCastClicked
            )
        }

    }
}

@Composable
private fun CastUi(
    casts: List<Cast>, modifier: Modifier,
    onItemCastClicked: (Long) -> Unit
) {
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - 30.dp) / 4
    val numRow = if (casts.size > 6) {
        2
    } else {
        1
    }
    val itemHeight = itemWidth * numRow
    CardCast(
        modifier = modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = 15.dp,
            bottom = 10.dp
        )
    )
    {
        LazyHorizontalGrid(rows = GridCells.Fixed(numRow), modifier = Modifier.height(itemHeight)) {
            items(
                casts,
                itemContent = { item: Cast ->
                    AvatarCast(
                        modifier = modifier,
                        itemWidth = itemWidth,
                        item = item,
                        onItemCastClicked = onItemCastClicked
                    )
                })
        }
    }
}

@Composable
fun CardCast(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(
                    topStart = 11.dp,
                    topEnd = 11.dp,
                    bottomEnd = 11.dp,
                    bottomStart = 11.dp
                ),
                clip = false,
                ambientColor = AppTheme.colors.colorBackgroundTheme,
                spotColor = Color.Black,
            ),
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(

            bottomEnd = 7.dp,
            bottomStart = 7.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.colorBackgroundTheme,
        ),
        content = content
    )
}

@Composable
private fun AvatarCast(
    modifier: Modifier,
    itemWidth: Dp,
    item: Cast,
    onItemCastClicked: (Long) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .width(itemWidth)
            .height(itemWidth)
            .clickable {
                onItemCastClicked.invoke(item.id)
            }
    ) {
        val (photo, info) = createRefs()
        AsyncImageView(data = item.profilePath.asPhotoUrl(PhotoSize.Profile.w185),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(itemWidth)
                .height(itemWidth)
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
    }
}
