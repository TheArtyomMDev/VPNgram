package sds.guardvpn.ui.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import sds.guardvpn.R
import sds.guardvpn.ui.permissions.components.PermissionItem
import sds.guardvpn.ui.theme.*


@Composable
fun PermissionsScreen(
    modifier: Modifier = Modifier,
    closeDialog: () -> Unit
) {
    val vm: PermissionsViewModel = koinViewModel()

    val permissionsList by vm.permissionsList.collectAsState()

    Column(
        modifier = modifier
            .clip(roundedSmallShape)
            .background(Gray80)
            .padding(MediumDimen)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.End)
                .size(25.dp)
                .clickable {
                    closeDialog()
                }
        )

        Text(
            stringResource(R.string.give_permissions),
            style = Typography.bodySmall,
            color = Gray70,
            modifier = Modifier
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            permissionsList.forEach { permission ->
                Spacer(Modifier.height(20.dp))

                PermissionItem(
                    modifier = Modifier,
                    permission = permission
                ) {
                    vm.updateIsAllGranted()
                }
            }
        }
    }


}

