package sds.vpn.gram.ui.splash

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sds.vpn.gram.common.Constants
import sds.vpn.gram.common.DeviceUtils
import sds.vpn.gram.common.ResourceProvider
import sds.vpn.gram.domain.model.Server
import sds.vpn.gram.domain.repository.ServerRepository
import sds.vpn.gram.domain.repository.UserRepository


class SplashScreenViewModel(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
    private val dataStore: DataStore<Preferences>,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _splashScreenState: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState.Loading)
    val splashScreenState = _splashScreenState.asStateFlow()
    private var servers = MutableStateFlow<List<Server>>(listOf())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = dataStore.data.first()
                if (prefs[Constants.IS_REGISTERED] != true) {
                    servers.emit(
                        userRepository.registerNewUser(
                            DeviceUtils.getAndroidID(resourceProvider.context)
                        )
                    )
                    dataStore.edit { it[Constants.IS_REGISTERED] = true }
                } else {
                    servers.emit(
                        serverRepository.getServers(
                            DeviceUtils.getAndroidID(resourceProvider.context)
                        )
                    )
                }
                _splashScreenState.emit(SplashScreenState.Successful)
            } catch (_: Exception) {
                _splashScreenState.emit(SplashScreenState.Error)
            }
        }
    }
}