package sds.guardvpn.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import sds.guardvpn.ui.theme.VPNgramTheme
import sds.guardvpn.ui.webview.WebViewScreen

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.extras?.getString("url")
        val openPremium = intent.extras?.getBoolean("openPremium")
        val openHome = intent.extras?.getBoolean("openHome")

        setContent {
            VPNgramTheme {
                if (url != null) {
                    WebViewScreen(
                        url = url,
                        openPremium = openPremium ?: false,
                        openHome = openHome ?: false
                    )
                }
            }
        }
    }
}