package com.vvpn.ui.premium.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.vvpn.R
import com.vvpn.ui.WebViewActivity
import com.vvpn.ui.theme.Gray70
import com.vvpn.ui.theme.Typography

@Composable
fun TermsText(header: String, link: String) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        Column {
            val context = LocalContext.current
            Text(
                header,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        context.startActivity(intent)
                    },
                style = Typography.labelMedium,
                color = Gray70,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}