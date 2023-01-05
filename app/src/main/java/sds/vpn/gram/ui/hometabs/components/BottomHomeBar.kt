package sds.vpn.gram.ui.hometabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import sds.vpn.gram.R
import sds.vpn.gram.ui.destinations.DirectionDestination
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.theme.MediumDimen
import sds.vpn.gram.ui.theme.RootDimen

@Composable
fun BottomHomeBar(
    navigator: DestinationsNavigator,
    modifier: Modifier
) {

    Box(
        modifier = modifier
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.bottom_background),
            contentDescription = "bottom_bar",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        val items = listOf(
            BottomHomeBarItem.Home, BottomHomeBarItem.Settings, BottomHomeBarItem.Profile
        )

        var currentDestination: DirectionDestination = HomeScreenDestination
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
                        .padding(top = RootDimen * 3, bottom = RootDimen * 1.5F)
                        .clickable {
                            currentDestination = it.destination
                            navigator.navigate(it.destination)
                        }
                )
            }
        }

    }

}

sealed class BottomHomeBarItem(
    val icon: Int,
    val unselectedIcon: Int,
    val destination: DirectionDestination,
) {
    object Home : BottomHomeBarItem(R.drawable.logo, R.drawable.logo, HomeScreenDestination)
    object Settings : BottomHomeBarItem(R.drawable.logo, R.drawable.logo, HomeScreenDestination)
    object Profile : BottomHomeBarItem(R.drawable.logo, R.drawable.logo, HomeScreenDestination)
}