package sds.vpn.gram.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import sds.vpn.gram.R
import sds.vpn.gram.common.VpnService
import sds.vpn.gram.ui.destinations.HomeScreenDestination
import sds.vpn.gram.ui.destinations.PremiumScreenDestination
import sds.vpn.gram.ui.destinations.TypedDestination
import sds.vpn.gram.ui.splash.DeepLinkArgs
import sds.vpn.gram.ui.theme.Gray80
import sds.vpn.gram.ui.theme.VPNgramTheme

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
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.background),
                contentDescription = "logo",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )

            rememberSystemUiController().setStatusBarColor(
                color = Gray80,
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