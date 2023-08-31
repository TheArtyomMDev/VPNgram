package com.vvpn.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.vvpn.R
import com.vvpn.common.Constants
import com.vvpn.common.VpnService
import com.vvpn.ui.destinations.HomeScreenDestination
import com.vvpn.ui.destinations.PremiumScreenDestination
import com.vvpn.ui.destinations.TypedDestination
import com.vvpn.ui.splash.DeepLinkArgs
import com.vvpn.ui.theme.Gray80
import com.vvpn.ui.theme.VPNgramTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openPremium = intent.extras?.getBoolean("openPremium")
        val openHome = intent.extras?.getBoolean("openHome")

        val destination =
            if(openPremium == true) PremiumScreenDestination
            else if(openHome == true) HomeScreenDestination
            else null

        setContent {
            MainApp(destination)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, VpnService::class.java))
        } else {
            startService(Intent(this, VpnService::class.java))
        }
    }
}

@Composable
fun MainApp(destination: TypedDestination<out Any>?) {
    VPNgramTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
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

            if(!isDark)
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.background),
                    contentDescription = "logo",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )

            rememberSystemUiController().setStatusBarColor(
                color = MaterialTheme.colorScheme.background,
            )

            DestinationsNavHost(
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(DeepLinkArgs(destination))
                }
            )
        }
    }
}

@Preview
@Composable
fun App() {
    MainApp(destination = null)
}