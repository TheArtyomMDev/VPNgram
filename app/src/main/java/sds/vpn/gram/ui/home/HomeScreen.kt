package sds.vpn.gram.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import sds.vpn.gram.ui.home.components.Switch
import sds.vpn.gram.ui.home.components.TopBar
import sds.vpn.gram.ui.hometabs.HomeTabsNavGraph
import sds.vpn.gram.ui.theme.Grey40
import sds.vpn.gram.ui.theme.Grey70
import sds.vpn.gram.ui.theme.Grey80
import sds.vpn.gram.ui.theme.RootDimen


@HomeTabsNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

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
            var isConnected by remember { mutableStateOf(false) }

            Switch(
                modifier = Modifier.width(150.dp)
            ) {
                isConnected = it
            }

            Spacer(Modifier.height(20.dp))

            if(isConnected) {
                Text("Connected", color = Grey40)
            } else {
                Text("Disconnected", color = Grey70)
            }
        }
    }
}