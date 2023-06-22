package sds.guardvpn.ui.countries

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sds.guardvpn.common.ResourceProvider
import sds.guardvpn.domain.model.Server
import sds.guardvpn.domain.repository.ServerRepository


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