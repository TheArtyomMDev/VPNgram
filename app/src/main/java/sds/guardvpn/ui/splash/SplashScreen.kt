package sds.guardvpn.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import sds.guardvpn.R
import sds.guardvpn.ui.destinations.HomeTabsScreenDestination


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {

    val vm: SplashScreenViewModel = koinViewModel()

    val startTime by remember { mutableStateOf(System.currentTimeMillis()) }

    val screenState = vm.splashScreenState.collectAsState().value
    LaunchedEffect(key1 = screenState) {
        when (screenState) {
            is SplashScreenState.Error -> {}
            is SplashScreenState.Successful -> {

                val endTime = System.currentTimeMillis()
                if(endTime - startTime < 3000) delay(3000 - (endTime - startTime))

                navigator.navigate(HomeTabsScreenDestination)
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
