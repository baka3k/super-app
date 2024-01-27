package hi.baka.feature.movie.detail.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baka3k.architecture.core.ui.component.AsyncImageView
import com.baka3k.architecture.core.ui.theme.AppTheme
import com.baka3k.core.data.movie.model.PhotoSize.Profile.w45
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.model.Review
import hi.baka.feature.movie.detail.MovieDetailUiState


@Composable
fun ReviewScreen(
    movieDetailUiState: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {
    when (movieDetailUiState) {
        is MovieDetailUiState.Success -> {
            val reviews = movieDetailUiState.reviews
            if (reviews.isNotEmpty()) {
                Title(text = "Reviews")
                ReviewItems(reviews = reviews, modifier = modifier)
            }
        }

        else -> {

        }
    }
}

@Composable
fun ReviewItems(reviews: List<Review>, modifier: Modifier) {
    val subList = if (reviews.size > 5) { // show max 5 review
        reviews.subList(0, 5)
    } else {
        reviews
    }
    subList.forEach { review ->
//        reviewItem2(review, modifier)
        ReviewItem(modifier, review)
    }
}

@Composable
private fun ReviewItem(
    modifier: Modifier, review: Review
) {
    ExpandableText(
        modifier = modifier.padding(start = 11.dp, end = 11.dp, top = 7.dp),
        text = review.content,
        style = MaterialTheme.typography.bodySmall.copy(
            AppTheme.colors.colorContentEditText
        )
    )
}

@Composable
private fun ReviewItem2(
    review: Review, modifier: Modifier
) {
    UiReviewItem(
        icon = {
            if (review.avatarPath.isNotEmpty()) {
                AsyncImageView(
                    data = review.avatarPath.asPhotoUrl(w45),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(5.dp)
                )
            }
        },
        modifier = modifier.padding(start = 11.dp, end = 11.dp, top = 7.dp),
        text = review.content,
        style = MaterialTheme.typography.bodySmall.copy(AppTheme.colors.colorContentEditText)
    )
}

@Composable
fun UiReviewItem(
    icon: @Composable () -> Unit?,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
        .then(modifier)) {
        val inlineContentPhotoMap = inlineContentPhotoMap(icon)
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = annotatedString(
                clickable,
                isExpanded,
                text,
                showLessStyle,
                showLessText,
                lastCharIndex,
                showMoreText,
                showMoreStyle
            ),
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign,
            inlineContent = inlineContentPhotoMap
        )
    }
}

@Composable
private fun annotatedString(
    clickable: Boolean,
    isExpanded: Boolean,
    text: String,
    showLessStyle: SpanStyle,
    showLessText: String,
    lastCharIndex: Int,
    showMoreText: String,
    showMoreStyle: SpanStyle
) = buildAnnotatedString {
    appendInlineContent(id = "imageId")
    if (clickable) {
        if (isExpanded) {
            append(text)
            withStyle(style = showLessStyle) { append(showLessText) }
        } else {
            val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                .dropLast(showMoreText.length)
                .dropLastWhile { Character.isWhitespace(it) || it == '.' }
            append(adjustText)
            withStyle(style = showMoreStyle) { append(showMoreText) }
        }
    } else {
        append(text)
    }
}

@Composable
private fun inlineContentPhotoMap(icon: @Composable () -> Unit?): Map<String, InlineTextContent> {
    val inlineContentPhotoMap = mapOf("imageId" to InlineTextContent(
        Placeholder(30.sp, 30.sp, PlaceholderVerticalAlign.TextCenter)
    ) {
        icon()
    })
    return inlineContentPhotoMap
}