package com.vvpn.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.vvpn.R
import com.vvpn.common.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


@Composable
fun TopBar(
    modifier: Modifier
) {
    Row(

        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        val dataStoreDef = get<DataStore<Preferences>>()
        val dataStore = dataStoreDef.data


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

        Spacer(Modifier.weight(1.1f))
        Image(
            imageVector = if(isDark) ImageVector.vectorResource(R.drawable.logo_night)
            else ImageVector.vectorResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
        )

        Spacer(Modifier.width(15.dp))

        Image(
            imageVector = if(isDark) ImageVector.vectorResource(R.drawable.app_name_night)
            else ImageVector.vectorResource(R.drawable.app_name),
            contentDescription = "app name",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(10.dp)
                .align(Alignment.CenterVertically)
        )

        Spacer(Modifier.weight(1.0f))


        val scope = rememberCoroutineScope()
        Image(
            imageVector = if(isDark) ImageVector.vectorResource(R.drawable.theme_switch_night)
            else ImageVector.vectorResource(R.drawable.theme_switch),
            contentDescription = "app name",
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    scope.launch {
                        dataStoreDef.edit {
                            val isDarkTheme = it[Constants.IS_DARK_THEME]
                            if (isDarkTheme != null) {
                                it[Constants.IS_DARK_THEME] = !isDarkTheme
                            } else {
                                it[Constants.IS_DARK_THEME] = true
                            }

                        }
                    }
                }
        )
    }
}