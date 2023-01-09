package sds.vpn.gram.ui.hometabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.destination
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import sds.vpn.gram.R
import sds.vpn.gram.ui.destinations.CountriesScreenDestination
import sds.vpn.gram.ui.destinations.DirectionDestination
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.destinations.PremiumScreenDestination
import sds.vpn.gram.ui.splash.DeepLinkArgs
import sds.vpn.gram.ui.theme.*

@Composable
fun BottomHomeBar(
    navigator: NavController,
    modifier: Modifier,
    deepLinkArgs: DeepLinkArgs
) {
    var barHeight by remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current

    LaunchedEffect(key1 = Unit) {
        deepLinkArgs.destination?.let { navigator.navigate(it.baseRoute) }
    }

    Box(
        modifier = modifier
            .neumorphic(
                neuShape = punchedSmallShape,
                lightShadowColor = Gray90,
                darkShadowColor = Color.White,
                elevation = 16.dp,
                strokeWidth = 3.dp,
                neuInsets = NeuInsets(10.dp, 12.dp)
            )
            .clip(shape = roundedSmallShapeTopCorners)
            .background(Gray80)
            .onSizeChanged {
                with(localDensity) {
                    barHeight = it.height.toDp()
                }
            }
    ) {
        val items = listOf(
            BottomHomeBarItem.Home, BottomHomeBarItem.Countries, BottomHomeBarItem.Premium
        )

        val currentDestination = navigator.currentBackStackEntryAsState().value?.destination()

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.
                    matchParentSize()
        ) {
            items.forEach {
                Image(
                    imageVector =
                        if(it.destination == currentDestination) ImageVector.vectorResource(it.icon)
                        else ImageVector.vectorResource(it.unselectedIcon),
                    contentDescription = "logo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = barHeight / 3F, bottom = barHeight / 3F)
                        .clickable {
                            navigator.navigate(it.destination.baseRoute)
                        }
                )
            }
        }

    }

}

sealed class BottomHomeBarItem(
    val icon: Int,
    val unselectedIcon: Int,
    val destination: DestinationSpec<*>,
) {
    object Home : BottomHomeBarItem(R.drawable.home_selected, R.drawable.home, HomeScreenDestination)
    object Countries : BottomHomeBarItem(R.drawable.countries_selected, R.drawable.countries, CountriesScreenDestination)
    object Premium : BottomHomeBarItem(R.drawable.premium_selected, R.drawable.premium, PremiumScreenDestination)
}