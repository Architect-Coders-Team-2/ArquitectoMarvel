package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class InternetConnectionManager(
    context: Context,
    lifecycle: Lifecycle,
    private val isAvaibleInternet: (Boolean) -> Unit
) : LifecycleObserver {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder().addCapability(NET_CAPABILITY_INTERNET).build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(network) ?: return
                if (networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (doesNetworkHasInternet()) {
                            withContext(Dispatchers.Main) {
                                validNetworks.add(network)
                                isAvaibleInternet(true)
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
                    isAvaibleInternet(true)
                }
            }
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            isAvaibleInternet(validNetworks.isNotEmpty())
        }
    }

    private fun doesNetworkHasInternet(): Boolean {
        return try {
            val socket =
                SocketFactory.getDefault().createSocket() ?: throw IOException(SOCKET_NULL)
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            true
        } catch (e: IOException) {
            Timber.e(e)
            false
        }
    }
}
