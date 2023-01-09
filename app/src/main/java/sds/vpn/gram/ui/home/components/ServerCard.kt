package sds.vpn.gram.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import sds.vpn.gram.R
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.ui.theme.*

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
        modifier = (if(isPressed) modifierPressed else modifier)
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

            Text(server.country, style = Typography.bodyMedium, color = Gray20)

            Spacer(Modifier.weight(1f))

            Text("${server.ping} ${stringResource(R.string.ms)}", style = Typography.bodyMedium, color = Gray40)

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