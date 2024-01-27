package com.baka3k.architecture.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.baka3k.architecture.core.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiTopAppBar(
    title: String = "",
    scrollBehavior: TopAppBarScrollBehavior? = null,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleSmall) },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = AppTheme.colors.colorContentEditText
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}
