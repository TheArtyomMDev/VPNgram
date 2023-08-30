package com.vvpn.ui.premium

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_VIEW
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import org.koin.androidx.compose.koinViewModel
import com.vvpn.R
import com.vvpn.common.DeviceUtils
import com.vvpn.common.InviteLinkBuilder
import com.vvpn.domain.model.TrafficType
import com.vvpn.ui.WebViewActivity
import com.vvpn.ui.home.components.TopBar
import com.vvpn.ui.hometabs.HomeTabsNavGraph
import com.vvpn.ui.premium.components.TermsText
import com.vvpn.ui.theme.*


@HomeTabsNavGraph()
@Destination
@Composable
fun PremiumScreen(
    navigator: DestinationsNavigator
) {
    val vm: PremiumViewModel = koinViewModel()

    val context = LocalContext.current

    val trafficLimitConfig = vm.trafficLimitConfig.collectAsState().value
    val trafficSpent = vm.trafficLimitConfig.collectAsState().value.trafficSpent
    val inviteLink by vm.inviteLinkFlow.collectAsState()
    val paymentLink by vm.paymentLinkFlow.collectAsState()
    val cost by vm.costFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val boxModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = RootDimen)
            .neumorphic(
                neuShape = punchedSmallShape,
                lightShadowColor = Gray90,
                darkShadowColor = Color.LightGray,
                elevation = 5.dp,
                strokeWidth = 5.dp,
                neuInsets = NeuInsets(5.dp, 5.dp)
            )
            .clip(shape = roundedSmallShape)
            .background(Black10)

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            TopBar(
                modifier = Modifier
                    .height(55.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(RootDimen)
            )

            Box(
                modifier = boxModifier
                    .background(Red50)
            ) {
                Column(
                    modifier = Modifier
                        .padding(RootDimen)
                ) {
                    Text(
                        stringResource(R.string.everyday_usage),
                        style = Typography.bodyLarge,
                        color = Color.White
                    )

                    Spacer(Modifier.height(3.dp))

                    when(trafficLimitConfig.trafficType) {
                        is TrafficType.Free -> {
                            Text(
                                stringResource(R.string.free),
                                style = Typography.bodyMedium,
                                color = Color.White
                            )

                            Spacer(Modifier.height(RootDimen))

                            Text(
                                "${trafficSpent.toInt()} / ${trafficLimitConfig.trafficType.trafficLimit} MB",
                                style = Typography.titleLarge,
                                color = Color.White
                            )
                        }
                        is TrafficType.Unlimited -> {
                            Text(
                                stringResource(R.string.unlimited),
                                style = Typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }


                }
            }

            Spacer(Modifier.height(MediumDimen))

            Box(
                modifier = boxModifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(60.dp)
                        .padding(SmallDimen)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {

//                            val inviteBuilder = InviteLinkBuilder()
//                            inviteBuilder.setAndroidId(DeviceUtils.getAndroidID(context))

                            val sendLinkIntent = Intent(ACTION_SEND)
                            sendLinkIntent.type = "text/plain"
                            sendLinkIntent.putExtra(Intent.EXTRA_TEXT, inviteLink)
                            context.startActivity(
                                Intent.createChooser(
                                    sendLinkIntent,
                                    "Share link"
                                )
                            )
                        }
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.invite_friend),
                        contentDescription = "invite friend",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                    )

                    Spacer(Modifier.weight(1F))

                    Text(
                        stringResource(R.string.invite_friend),
                        style = Typography.bodyMedium,
                        color = Color.White
                    )

                    Spacer(Modifier.weight(1F))
                }
            }
        }

        if(trafficLimitConfig.trafficType is TrafficType.Free)
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
                        Text(
                            stringResource(R.string.unlimited),
                            style = Typography.bodyMedium,
                            color = Color.White
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            cost,
                            style = Typography.titleMedium,
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(MediumDimen))

                Box(
                    modifier = Modifier
                        .padding(horizontal = RootDimen)
                        .neumorphic(
                            neuShape = punchedSmallShape,
                            lightShadowColor = Gray90,
                            darkShadowColor = Color.LightGray,
                            elevation = 5.dp,
                            strokeWidth = 5.dp,
                            neuInsets = NeuInsets(5.dp, 5.dp)
                        )
                        .clip(shape = roundedSmallShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            val intent = Intent(context, WebViewActivity::class.java)
                            intent.putExtra("url", paymentLink)

                            context.startActivity(intent)
                        }
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
                        style = Typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                Spacer(Modifier.height(MediumDimen))

                Box(
                    modifier = boxModifier
                ) {
                    Column(
                        modifier = Modifier
                            .padding(RootDimen)
                    ) {
                        val terms = mapOf(
                            "Условия подписки" to "https://vpngram.pro/SubscriptionTerms.html",
                            "Условия использования" to "https://vpngram.pro/TermsofUse.html",
                            "Политика конфиденциальности" to "https://vpngram.pro/PrivacyPolicy.html",
                        )

                        terms.forEach {
                            Spacer(Modifier.height(2.dp))
                            TermsText(it.key, it.value)
                            Spacer(Modifier.height(2.dp))
                        }

                    }
                }

                Spacer(Modifier.height(1.7*RootDimen))
            }
    }
}