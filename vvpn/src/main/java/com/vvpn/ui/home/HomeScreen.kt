package com.vvpn.ui.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.nikhilchaudhari.library.NeuInsets
import me.nikhilchaudhari.library.neumorphic
import me.nikhilchaudhari.library.shapes.Pressed
import me.nikhilchaudhari.library.shapes.Punched
import org.koin.androidx.compose.koinViewModel
import com.vvpn.R
import com.vvpn.domain.model.Server
import com.vvpn.domain.model.TrafficType
import com.vvpn.ui.home.components.ProgressBar
import com.vvpn.ui.home.components.ServerCard
import com.vvpn.ui.home.components.Switch
import com.vvpn.ui.home.components.TopBar
import com.vvpn.ui.hometabs.HomeTabsNavGraph
import com.vvpn.ui.permissions.PermissionsScreen
import com.vvpn.ui.theme.Gray70
import com.vvpn.ui.theme.Gray80
import com.vvpn.ui.theme.Gray90
import com.vvpn.ui.theme.Orange50
import com.vvpn.ui.theme.Purple50
import com.vvpn.ui.theme.RootDimen
import com.vvpn.ui.theme.Typography
import com.vvpn.ui.destinations.CountriesScreenDestination
import com.vvpn.ui.destinations.PremiumScreenDestination
import com.vvpn.ui.theme.Orange40
import com.vvpn.ui.theme.Red50
import com.vvpn.ui.theme.pressedSmallShape
import com.vvpn.ui.theme.punchedSmallShape
import com.vvpn.ui.theme.roundedSmallShape


@HomeTabsNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    serverSent: Server? = null,
) {
    val vm: HomeViewModel = koinViewModel()

    var isConnected by remember { mutableStateOf<Boolean?>(null) }
    val lastUsedServer by vm.lastUsedServer.collectAsState(Server("", "", "", 80, ""))
    var chosenServer by remember { mutableStateOf(vm.getChosenServer()) }
    val isAllGranted by vm.isAllGranted.collectAsState()
    val showPermissionRequest by vm.showPermissionsRequest.collectAsState()
    var openDialog by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val intentSender = vm.vpnService.getVpnPrepareIntent()
        if (intentSender == null) {
            vm.connectToServer(vm.getChosenServer())
        }
    }

    LaunchedEffect(key1 = Unit) {
        if(vm.isVpnConnected() && serverSent != null && serverSent != lastUsedServer) {
            vm.disconnectFromServer(lastUsedServer)
            isConnected = false
        }
        else isConnected = vm.isVpnConnected()
    }

    if(isConnected == true)
        chosenServer = lastUsedServer
    if(isConnected == false) {
        chosenServer = if(serverSent == null && vm.servers.collectAsState().value.isEmpty())
            Server("", "", "", 80, "")
        else serverSent ?: vm.servers.collectAsState().value[0]
    }

    val trafficConfig = vm.trafficLimitResponse.collectAsState().value

    if(isAllGranted == false && openDialog && showPermissionRequest) {
        Dialog(onDismissRequest = {}) {
            PermissionsScreen {
                openDialog = false

                vm.updateIsAllGranted()
                if(isAllGranted == false) {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(200)
                        openDialog = true
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.TopCenter)
                .padding(RootDimen)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isConnected != null) {
                Switch(
                    initialState = isConnected!!,
                    modifier = Modifier
                        .width(150.dp)
                ) {
                    isConnected = it

                    if (isConnected!!) {
                        when(trafficConfig.trafficType) {
                            is TrafficType.Unlimited -> {
                                val intentSender = vm.vpnService.getVpnPrepareIntent()

                                if (intentSender != null) {
                                    vm.setChosenServer(chosenServer)
                                    launcher.launch(intentSender)
                                } else vm.connectToServer(chosenServer)

                            }
                            is TrafficType.Free -> {
                                if (trafficConfig.trafficType.trafficLimit - trafficConfig.trafficSpent > 0) {
                                    val intentSender = vm.vpnService.getVpnPrepareIntent()

                                    if (intentSender != null) {
                                        vm.setChosenServer(chosenServer)
                                        launcher.launch(intentSender)
                                    } else vm.connectToServer(chosenServer)
                                }
                                else navigator.navigate(PremiumScreenDestination)
                            }
                        }
                    }
                    else {
                        vm.disconnectFromServer(chosenServer)
                    }
                }

                Spacer(Modifier.height(30.dp))

                if (isConnected!!) {
                    Text(stringResource(R.string.connected), style = Typography.bodyMedium)
                } else {
                    Text(
                        stringResource(R.string.disconnected),
                        style = Typography.bodyMedium,
                        color = Gray70
                    )
            }
        }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(RootDimen)
        ) {
            if(trafficConfig.trafficType is TrafficType.Free) {
                Text(
                    "${stringResource(R.string.available)} ${trafficConfig.trafficType.trafficLimit.toInt()} MB",
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.End)
                )

                Spacer(Modifier.height(10.dp))

                ProgressBar(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .height(10.dp),
                    backgroundColor = Color.White,
                    foregroundColor =
                    Brush.horizontalGradient(listOf(Red50, Red50)),
                    progress = (trafficConfig.trafficSpent / trafficConfig.trafficType.trafficLimit).toFloat()
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    "${stringResource(R.string.spent)} ${trafficConfig.trafficSpent.toInt()} MB",
                    style = Typography.bodyMedium
                )
            }

            Spacer(Modifier.height(30.dp))

            val cardModifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .neumorphic(
                    neuShape = punchedSmallShape,
                    lightShadowColor = Gray90,
                    darkShadowColor = Color.LightGray,
                    elevation = 16.dp,
                    strokeWidth = 5.dp,
                    neuInsets = NeuInsets(10.dp, 12.dp)
                )
                .clip(shape = roundedSmallShape)
                .background(Gray80)

            val cardModifierPressed = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(shape = roundedSmallShape)
                .neumorphic(
                    neuShape = pressedSmallShape,
                    lightShadowColor = Gray90,
                    darkShadowColor = Color.LightGray,
                    elevation = 16.dp,
                    strokeWidth = 5.dp,
                    neuInsets = NeuInsets(10.dp, 12.dp)
                )
                .background(Gray80)

            ServerCard(
                chosenServer,
                cardModifier,
                cardModifierPressed,
            ) {
                navigator.navigate(CountriesScreenDestination)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

