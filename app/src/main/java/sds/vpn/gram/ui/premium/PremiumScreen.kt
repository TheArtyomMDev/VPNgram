package sds.vpn.gram.ui.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import org.koin.androidx.compose.koinViewModel
import sds.vpn.gram.R
import sds.vpn.gram.ui.home.components.TopBar
import sds.vpn.gram.ui.hometabs.HomeTabsNavGraph
import sds.vpn.gram.ui.theme.*


@OptIn(ExperimentalGlideComposeApi::class)
@HomeTabsNavGraph()
@Destination
@Composable
fun PremiumScreen(
    navigator: DestinationsNavigator
) {
    val vm: PremiumViewModel = koinViewModel()

    val trafficLimit = vm.trafficLimitResponse.collectAsState().value.trafficLimit
    val trafficSpent = vm.trafficLimitResponse.collectAsState().value.trafficSpent

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.TopStart)
                .padding(RootDimen)
        )

        val boxModifier = Modifier
            .fillMaxWidth()
            .padding(RootDimen)
            .neumorphic(
                neuShape = punchedSmallShape,
                lightShadowColor = Gray90,
                darkShadowColor = Color.LightGray,
                elevation = 16.dp,
                strokeWidth = 5.dp,
                neuInsets = NeuInsets(10.dp, 12.dp)
            )
            .clip(shape = roundedSmallShape)
            .background(Gray80)

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Box(
                modifier = boxModifier
            ) {
                Column(
                    modifier = Modifier
                        .padding(RootDimen)
                ) {
                    Text(stringResource(R.string.everyday_usage), style = Typography.bodyLarge)

                    Spacer(Modifier.height(5.dp))

                    Text(
                        stringResource(R.string.free),
                        style = Typography.bodyMedium,
                        color = Gray70
                    )

                    Spacer(Modifier.height(RootDimen))

                    Text(
                        "${(trafficLimit - trafficSpent).toInt()} / ${trafficLimit.toInt()} MB",
                        style = Typography.titleLarge
                    )
                }
            }

            Box(
                modifier = boxModifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(70.dp)
                        .padding(SmallDimen)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.invite_friend),
                        contentDescription = "invite friend",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                    )

                    Spacer(Modifier.weight(1F))

                    Text(stringResource(R.string.invite_friend), style = Typography.bodyLarge)

                    Spacer(Modifier.weight(1F))
                }
            }
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Box(
                modifier = boxModifier
            ) {
                Column(
                    modifier = Modifier
                        .padding(RootDimen)
                ) {
                    Text(stringResource(R.string.unlimited), style = Typography.bodyLarge, color = Gray70)

                    Spacer(Modifier.height(5.dp))

                    Text(
                        "250 руб / мес",
                        style = Typography.titleLarge,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(RootDimen)
                    .neumorphic(
                        neuShape = punchedSmallShape,
                        lightShadowColor = Gray90,
                        darkShadowColor = Color.LightGray,
                        elevation = 16.dp,
                        strokeWidth = 5.dp,
                        neuInsets = NeuInsets(10.dp, 12.dp)
                    )
                    .clip(shape = roundedSmallShape)
            
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.subscribe_background),
                    contentDescription = "subscribe",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    stringResource(R.string.subscribe),
                    style = Typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}