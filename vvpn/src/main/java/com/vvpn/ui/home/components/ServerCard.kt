package com.vvpn.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vvpn.R
import com.vvpn.domain.model.Server
import com.vvpn.ui.theme.Black10
import com.vvpn.ui.theme.Gray20
import com.vvpn.ui.theme.Gray40
import com.vvpn.ui.theme.RootDimen
import com.vvpn.ui.theme.Typography


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ServerCard(
    server: Server,
    modifier: Modifier,
    modifierPressed: Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Box(
        modifier = (if (isPressed) modifierPressed else modifier)
            .background(Black10)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = server.imageUrl,
                contentDescription = "country",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = 75.dp)
                    .padding(10.dp)
            )

            Text(server.country, style = Typography.bodyMedium, color = Color.White)

            Spacer(Modifier.weight(1f))

            Text(
                "${stringResource(R.string.speed)} ${server.ping} ${stringResource(R.string.ms)}",
                style = Typography.bodySmall,
                color = Color.White,
                modifier = Modifier
                    .width(90.dp),
                textAlign = TextAlign.End
            )

            Spacer(Modifier.width(20.dp))

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_forward),
                contentDescription = "arrow",
                modifier = Modifier
                    .padding(end = 2 * RootDimen)
                    .height(20.dp)
            )
        }
    }
}