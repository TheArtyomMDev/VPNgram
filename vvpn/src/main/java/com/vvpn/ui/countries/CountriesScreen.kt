package com.vvpn.ui.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import org.koin.androidx.compose.koinViewModel
import com.vvpn.ui.destinations.HomeScreenDestination
import com.vvpn.ui.home.components.ServerCard
import com.vvpn.ui.home.components.TopBar
import com.vvpn.ui.hometabs.HomeTabsNavGraph
import com.vvpn.ui.theme.*


@HomeTabsNavGraph
@Destination
@Composable
fun CountriesScreen(
    navigator: DestinationsNavigator
) {
    val vm: CountriesViewModel = koinViewModel()

    val servers = vm.servers.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.CenterHorizontally)
                .padding(RootDimen)
        )

        val cardModifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
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

        val cardModifierPressed = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = roundedSmallShape)
            .neumorphic(
                neuShape = pressedSmallShape,
                lightShadowColor = Gray90,
                darkShadowColor = Color.LightGray,
                elevation = 16.dp,
                strokeWidth = 5.dp,
                neuInsets = NeuInsets(10.dp, 12.dp)
            )
            .background(Gray80)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = RootDimen),
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = RootDimen)
        ) {
            items(servers) { server ->
                ServerCard(
                    server = server,
                    modifier = cardModifier,
                    modifierPressed = cardModifierPressed
                ) {
                    navigator.navigate(HomeScreenDestination(serverSent = server))
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}