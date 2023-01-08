package sds.vpn.gram.ui.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import org.koin.androidx.compose.koinViewModel
import sds.vpn.gram.R
import sds.vpn.gram.ui.destinations.SplashScreenDestination
import sds.vpn.gram.ui.home.components.TopBar
import sds.vpn.gram.ui.permissions.components.PermissionItem
import sds.vpn.gram.ui.theme.*


@RootNavGraph
@Destination
@Composable
fun PermissionsScreen(
    navigator: DestinationsNavigator
) {
    val vm: PermissionsViewModel = koinViewModel()

    val isAllGranted by vm.isAllGranted.collectAsState()
    val permissionsList by vm.permissionsList.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        TopBar(
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.Start)
                .padding(RootDimen)
        )

        Text(
            stringResource(R.string.give_permissions),
            style = Typography.titleLarge,
            modifier = Modifier
                .padding(RootDimen)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RootDimen)
        ) {
            permissionsList.forEach { permission ->
                PermissionItem(
                    modifier = Modifier,
                    permission = permission
                ) {
                    vm.updateIsAllGranted()
                }

                Spacer(Modifier.height(20.dp))
            }
        }

        Spacer(Modifier.weight(1F))

        if(isAllGranted) {
            Box(
                modifier = Modifier
                    .padding(RootDimen)
                    .neumorphic(
                        neuShape = punchedSmallShape,
                        lightShadowColor = Gray90,
                        darkShadowColor = Color.LightGray,
                        elevation = 16.dp,
                        strokeWidth = 5.dp,
                        neuInsets = NeuInsets(10.dp, 12.dp)
                    )
                    .clip(shape = roundedSmallShape)
                    .clickable {
                        navigator.navigate(SplashScreenDestination)
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
                    stringResource(R.string.proceed),
                    style = Typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

    }


}

