package sds.vpn.gram.ui.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import me.nikhilchaudhari.library.shapes.Pressed
import me.nikhilchaudhari.library.shapes.Punched
import org.koin.androidx.compose.koinViewModel
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.home.components.ServerCard
import sds.vpn.gram.ui.home.components.TopBar
import sds.vpn.gram.ui.hometabs.HomeTabsNavGraph
import sds.vpn.gram.ui.theme.Gray80
import sds.vpn.gram.ui.theme.Gray90
import sds.vpn.gram.ui.theme.RootDimen


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
                .align(Alignment.Start)
                .padding(RootDimen)
        )

        val cardModifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .neumorphic(
                neuShape = Punched.Rounded(radius = 48.dp),
                lightShadowColor = Gray90,
                darkShadowColor = Color.LightGray,
                elevation = 16.dp,
                strokeWidth = 5.dp,
                neuInsets = NeuInsets(10.dp, 12.dp)
            )
            .clip(shape = RoundedCornerShape(48.dp))
            .background(Gray80)

        val cardModifierPressed = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = RoundedCornerShape(48.dp))
            .neumorphic(
                neuShape = Pressed.Rounded(radius = 48.dp),
                lightShadowColor = Gray90,
                darkShadowColor = Color.LightGray,
                elevation = 16.dp,
                strokeWidth = 5.dp,
                neuInsets = NeuInsets(10.dp, 12.dp)
            )
            .background(Gray80)

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
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
            }
        }
    }
}