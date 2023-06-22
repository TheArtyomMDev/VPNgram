package sds.guardvpn.ui.permissions.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import sds.guardvpn.ui.home.components.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import sds.guardvpn.R
import sds.guardvpn.domain.model.Permission
import sds.guardvpn.ui.theme.*

@Composable
fun PermissionItem(
    modifier: Modifier,
    permission: Permission,
    onPermissionGrantedResult: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    val context = LocalContext.current
    var isGranted by remember {
        mutableStateOf(permission.checkIsGranted(context))
    }

    val activityLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        isGranted = permission.checkIsGranted(context)
        onPermissionGrantedResult()
    }

    val modifierNonPressed = Modifier
        .neumorphic(
            neuShape = punchedSmallShape,
            lightShadowColor = Gray90,
            darkShadowColor = Color.LightGray,
            elevation = 16.dp,
            strokeWidth = 5.dp,
            neuInsets = NeuInsets(10.dp, 12.dp)
        )
        .clip(roundedSmallShape)
        .background(Gray80)

    val modifierPressed = modifier
        .clip(roundedSmallShape)
        .neumorphic(
            neuShape = pressedSmallShape,
            lightShadowColor = Gray90,
            darkShadowColor = Color.LightGray,
            elevation = 16.dp,
            strokeWidth = 5.dp,
            neuInsets = NeuInsets(10.dp, 12.dp)
        )
        .background(Gray80)

    var boxModifier = if (isPressed) modifierPressed else modifierNonPressed
    if(!isGranted) boxModifier = boxModifier
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            activityLauncher.launch(permission.getGrantIntent(context))
        }

    Box(
        modifier = boxModifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(RootDimen)
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ){
                Text(
                    stringResource(permission.nameResource),
                    style = Typography.bodySmall,
                    color = Gray20
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    stringResource(
                        if(permission.checkIsGranted(context)) R.string.granted
                        else R.string.not_granted
                    ),
                    style = Typography.bodySmall,
                    color = Gray70,
                )
            }

            if(isGranted)
                Switch(
                    modifier = Modifier
                        .width(80.dp),
                    isClickable = false,
                    defaultDimen = 4.dp,
                    initialState = true
                ) {}
            else Switch(
                modifier = Modifier
                    .width(80.dp),
                isClickable = false,
                defaultDimen = 4.dp,
                initialState = false
            ) {
                activityLauncher.launch(permission.getGrantIntent(context))
            }
        }

    }
}