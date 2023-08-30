package com.vvpn.ui.countries

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.vvpn.common.ResourceProvider
import com.vvpn.domain.model.Server
import com.vvpn.domain.repository.ServerRepository


class CountriesViewModel(
    private val serverRepository: ServerRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _servers = MutableStateFlow<List<Server>>(listOf())
    val servers = _servers.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _servers.emit(serverRepository.getServersFromDataStore())
        }
    }
}