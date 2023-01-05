package sds.vpn.gram.ui.hometabs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import sds.vpn.gram.ui.NavGraphs
import sds.vpn.gram.ui.hometabs.components.BottomHomeBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun HomeTabsScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomHomeBar(
                modifier = Modifier,
                navigator = navigator
            )
        }
    ) {
        DestinationsNavHost(navGraph = NavGraphs.homeTabs)
    }
}

@NavGraph
annotation class HomeTabsNavGraph(
    val start: Boolean = false
)