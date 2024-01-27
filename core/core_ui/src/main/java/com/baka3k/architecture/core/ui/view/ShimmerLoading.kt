package com.baka3k.architecture.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import com.baka3k.architecture.core.ui.component.ShimmerAnimation

class ShimmerLoading(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)

    init {
        val composeView = ComposeView(context)
        composeView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        composeView.setContent {
            ShimmerAnimation()
        }
        addView(composeView)
    }
}