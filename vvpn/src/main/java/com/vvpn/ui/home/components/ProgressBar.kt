package com.vvpn.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    modifier: Modifier,
    backgroundColor: Color,
    foregroundColor: Brush,
    progress: Float,
) {
    var fullBarWidth by remember { mutableStateOf(0.dp) }

    println(fullBarWidth)
    with(LocalDensity.current) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .onSizeChanged {
                    fullBarWidth = it.width.toDp()
                }
        ) {
            Box(
                modifier = modifier
                    .background(foregroundColor)
                    .width(fullBarWidth * progress)
            )
        }
    }
}