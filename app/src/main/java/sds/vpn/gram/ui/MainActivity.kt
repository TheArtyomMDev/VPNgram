package sds.vpn.gram.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import sds.vpn.gram.R
import sds.vpn.gram.ui.splash.DeepLinkArgs
import sds.vpn.gram.ui.theme.Grey80
import sds.vpn.gram.ui.theme.VPNgramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                        color = Grey80,
                    )

                    DestinationsNavHost(
                       navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}