package sds.vpn.gram.ui.premium

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
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

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(RootDimen)
        ) {

        }
    }
}