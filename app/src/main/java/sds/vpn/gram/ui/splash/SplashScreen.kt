package sds.vpn.gram.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import sds.vpn.gram.R
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
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.destinations.HomeTabsScreenDestination
import sds.vpn.gram.ui.destinations.PermissionsScreenDestination
import sds.vpn.gram.ui.hometabs.HomeTabsNavGraph


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {

    val vm: SplashScreenViewModel = koinViewModel()

    val isAllGranted by vm.isAllGranted.collectAsState()
    val startTime by remember {
        mutableStateOf(System.currentTimeMillis())
    }


    val screenState = vm.splashScreenState.collectAsState().value
    LaunchedEffect(key1 = screenState, key2 = isAllGranted) {
        when (screenState) {
            is SplashScreenState.Error -> {}
            is SplashScreenState.Successful -> {

                val endTime = System.currentTimeMillis()
                if(endTime - startTime < 3000) delay(3000 - (endTime - startTime))

                if(isAllGranted == true)
                    navigator.navigate(HomeTabsScreenDestination)
                else if(isAllGranted == false)
                    navigator.navigate(PermissionsScreenDestination)
            }
            is SplashScreenState.Loading -> {}
        }
    }

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
    }
}
