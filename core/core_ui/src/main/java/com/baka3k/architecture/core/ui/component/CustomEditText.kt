package com.baka3k.architecture.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.baka3k.architecture.core.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEditText(
    value: String = "",
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChanged: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    )
    {
        var valueState by rememberSaveable {
            mutableStateOf(value)
        }
        val colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Unspecified,
            unfocusedContainerColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
        )
        BasicTextField(
            value = valueState,
            enabled = true,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = AppTheme.colors.colorContentEditText),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .background(shape = CircleShape, color = AppTheme.colors.colorBgEditText)
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.padding(start = 5.dp),
                        tint = AppTheme.colors.colorContentEditText
                    )
                    TextFieldDefaults.DecorationBox(
                        colors = colors,
                        value = valueState,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        placeholder = {
                            Text(
                                "Search",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        contentPadding = PaddingValues(start = 5.dp, end = 20.dp),
                    )
                }

            },
            keyboardActions = keyboardActions,
            onValueChange = {
                valueState = it
                onValueChanged.invoke(it)
            },
        )
    }
}