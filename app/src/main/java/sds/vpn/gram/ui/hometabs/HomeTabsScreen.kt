package sds.vpn.gram.ui.hometabs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.rememberNavHostEngine
import sds.vpn.gram.ui.NavGraphs
import sds.vpn.gram.ui.hometabs.components.BottomHomeBar
import sds.vpn.gram.ui.splash.DeepLinkArgs


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RootNavGraph
@Destination
@Composable
fun HomeTabsScreen(
    deepLinkArgs: DeepLinkArgs
) {

    val navController = rememberNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.homeTabs,
            modifier = Modifier.weight(10f),
            navController = navController
        )
        BottomHomeBar(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            navigator = navController,
            deepLinkArgs = deepLinkArgs
        )
    }
}

@NavGraph
annotation class HomeTabsNavGraph(
    val start: Boolean = false
)