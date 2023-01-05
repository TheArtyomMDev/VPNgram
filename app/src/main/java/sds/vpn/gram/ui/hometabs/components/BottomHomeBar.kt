package sds.vpn.gram.ui.hometabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.destination
import sds.vpn.gram.R
import sds.vpn.gram.ui.destinations.CountriesScreenDestination
import sds.vpn.gram.ui.destinations.DirectionDestination
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.destinations.PremiumScreenDestination
import sds.vpn.gram.ui.theme.RootDimen

@Composable
fun BottomHomeBar(
    navigator: NavController,
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
            BottomHomeBarItem.Home, BottomHomeBarItem.Countries, BottomHomeBarItem.Premium
        )

        val currentDestination = navigator.currentBackStackEntryAsState().value?.destination()

        println(currentDestination)
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
                        .padding(top = RootDimen * 3.4F, bottom = RootDimen * 1.5F)
                        .clickable {
                            navigator.navigate(it.destination.baseRoute)
                            //navigator.navigateTo(it.destination as Direction)
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