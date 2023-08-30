package com.vvpn.ui.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.vvpn.ui.MainActivity


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    openPremium: Boolean = false,
    openHome: Boolean = false
) {

    val context = LocalContext.current

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
            backEnabled = view.canGoBack()
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val mainIntent = Intent(context, MainActivity::class.java)

            if(request?.url.toString().contains("activation.com")) {
                if(openPremium) {
                    mainIntent.putExtra("openPremium", true)
                }
                else if(openHome) {
                    mainIntent.putExtra("openHome", true)
                }
                context.startActivity(mainIntent)
                (context as Activity).finish()
            }
            else if(request?.url.toString().contains("close.com")) {
                (context as Activity).finish()
            }

            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setDownloadListener { url, _, _, _, _ ->
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        context.startActivity(i)
                    }
                    webViewClient = mWebViewClient
                    settings.apply {
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        builtInZoomControls = true
                        displayZoomControls = false
                        setSupportZoom(true)
                        javaScriptCanOpenWindowsAutomatically = true
                        defaultTextEncodingName = "utf-8"
                    }
                    loadUrl(url)
                }
            },
            update = { webView = it },
            modifier = Modifier
                .fillMaxSize()
        )
    }
    
    BackHandler {
        if(backEnabled) webView?.goBack()
    }
}