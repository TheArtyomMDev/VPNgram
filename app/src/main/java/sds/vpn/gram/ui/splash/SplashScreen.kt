package sds.vpn.gram.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import sds.vpn.gram.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.destinations.HomeTabsScreenDestination
import sds.vpn.gram.ui.hometabs.HomeTabsNavGraph


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    val vm: SplashScreenViewModel = koinViewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .width(150.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.app_name),
                contentDescription = "app name",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        val screenState = vm.splashScreenState.collectAsState().value
        LaunchedEffect(key1 = screenState) {
            when (screenState) {
                is SplashScreenState.Error -> {}
                is SplashScreenState.Successful -> {
                   navigator.navigate(HomeTabsScreenDestination)
                }
                is SplashScreenState.Loading -> {}
            }
        }
    }
}
