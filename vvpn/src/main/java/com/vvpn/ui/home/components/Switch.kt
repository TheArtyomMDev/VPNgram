package com.vvpn.ui.home.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.vvpn.R

@Composable
fun Switch(
    modifier: Modifier,
    defaultDimen: Dp = 10.dp,
    initialState: Boolean = false,
    isClickable: Boolean = true,
    onClick: (Boolean) -> Unit,
) {
    var isEnabled by remember { mutableStateOf(initialState) }

    var switchWidth by remember { mutableStateOf(0.dp) }
    var switchHeight by remember { mutableStateOf(0.dp) }

    var circleWidth by remember { mutableStateOf(50.dp) }

    val interactionSource = remember { MutableInteractionSource() }

    val duration = 500
    
    val offsetStart by animateDpAsState(
        animationSpec = tween(
            durationMillis = duration,
        ),
        targetValue = if(isEnabled) (switchWidth - circleWidth - 2*defaultDimen) else 0.dp
    )

    with(LocalDensity.current) {
        Box(
            modifier = modifier
                .onSizeChanged {
                    switchWidth = it.width.toDp()
                    switchHeight = it.height.toDp()
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if(isClickable) {
                        isEnabled = !isEnabled
                        onClick(isEnabled)
                    }
                    else {
                        onClick(isEnabled)
                    }
                }
        ) {

            Crossfade(
                targetState = isEnabled,
                animationSpec = tween(
                    durationMillis = duration,
                )
            ) { isEnabled ->
                if (isEnabled)
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.switch_on),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                else
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.switch_off),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
            }


            Image(
                imageVector = ImageVector.vectorResource(R.drawable.circle_switch),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .matchParentSize()
                    .padding(start = defaultDimen)
                    .wrapContentWidth()
                    .height(switchHeight - 2*defaultDimen)
                    .onSizeChanged {
                        circleWidth = it.width.toDp()
                    }
                    .offset {
                        IntOffset(
                            offsetStart
                                .toPx()
                                .toInt(), 0
                        )
                    }
            )
        }
    }
}