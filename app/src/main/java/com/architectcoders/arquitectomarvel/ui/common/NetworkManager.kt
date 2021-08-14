package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class NetworkManager(
    context: Context
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback: ConnectivityManager.NetworkCallback
    private val validNetworks: MutableSet<Network> = HashSet()
    private val _isInternetAvailable = MutableStateFlow(false)
    val isInternetAvailable: StateFlow<Boolean> get() = _isInternetAvailable

    init {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder().addCapability(NET_CAPABILITY_INTERNET).build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        _isInternetAvailable.value = false
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network) ?: return
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (doesNetworkHaveInternet()) {
                            withContext(Dispatchers.Main) {
                                validNetworks.add(network)
                                _isInternetAvailable.value = true
                            }
                        }
                    }
                }
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
                ) {
                    validNetworks.add(network)
                    _isInternetAvailable.value = true
                }
            }
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            _isInternetAvailable.value = validNetworks.isNotEmpty()
        }
    }

    private fun doesNetworkHaveInternet(): Boolean {
        return try {
            val socket =
                SocketFactory.getDefault().createSocket() ?: throw IOException(SOCKET_NULL)
            socket.connect(InetSocketAddress(PING_HOSTNAME, PING_PORT), PING_TIMEOUT)
            socket.close()
            true
        } catch (e: IOException) {
            Timber.e(e)
            false
        }
    }

    fun unregisterNetworkCallback() {
        validNetworks.clear()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
