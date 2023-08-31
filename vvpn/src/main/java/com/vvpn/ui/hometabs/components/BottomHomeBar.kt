package com.vvpn.ui.hometabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.destination
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import com.vvpn.R
import com.vvpn.common.Constants
import com.vvpn.ui.destinations.CountriesScreenDestination
import com.vvpn.ui.destinations.DirectionDestination
import com.vvpn.ui.destinations.HomeScreenDestination
import com.vvpn.ui.destinations.PremiumScreenDestination
import com.vvpn.ui.splash.DeepLinkArgs
import com.vvpn.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.get

@Composable
fun BottomHomeBar(
    navigator: NavController,
    modifier: Modifier,
    deepLinkArgs: DeepLinkArgs
) {
    val dataStore = get<DataStore<Preferences>>().data
    var isDark by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        dataStore.collectLatest { prefs ->
            prefs[Constants.IS_DARK_THEME]?.let {
                isDark = it
            }
        }
    }

    var barHeight by remember { mutableStateOf(0.dp) }

    val localDensity = LocalDensity.current

    LaunchedEffect(key1 = Unit) {
        deepLinkArgs.destination?.let { navigator.navigate(it.baseRoute) }
    }

    Box(
        modifier = modifier
            .clip(shape = roundedSmallShapeTopCorners)
            .border(2.dp, MaterialTheme.colorScheme.onBackground, roundedSmallShapeTopCorners)
            .background(MaterialTheme.colorScheme.background)
            .onSizeChanged {
                with(localDensity) {
                    barHeight = it.height.toDp()
                }
            }
    ) {
        val items = when(isDark) {
            true -> listOf(
                BottomHomeBarItemNight.Home, BottomHomeBarItemNight.Countries, BottomHomeBarItemNight.Premium
            )
            false -> listOf(
                BottomHomeBarItem.Home, BottomHomeBarItem.Countries, BottomHomeBarItem.Premium
            )
        }

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
                        .padding(top = barHeight / 4F, bottom = barHeight / 4F)
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

sealed class BottomHomeBarItemNight(
    val icon: Int,
    val unselectedIcon: Int,
    val destination: DestinationSpec<*>,
) {
    object Home : BottomHomeBarItem(R.drawable.home_selected_night, R.drawable.home_night, HomeScreenDestination)
    object Countries : BottomHomeBarItem(R.drawable.countries_night, R.drawable.countries_night, CountriesScreenDestination)
    object Premium : BottomHomeBarItem(R.drawable.premium_selected_night, R.drawable.premium_night, PremiumScreenDestination)
}