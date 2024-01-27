package com.baka3k.architecture.core.ui.view

import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.baka3k.architecture.core.ui.component.ShimmerAnimation

class ShimmerAdapter(private val numberItem: Int = 4) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val composeView = ComposeView(parent.context)
        return ShimmerHolder(composeView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return numberItem
    }

    class ShimmerHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            (itemView as ComposeView).setContent {
                ShimmerAnimation()
            }
        }
    }
}