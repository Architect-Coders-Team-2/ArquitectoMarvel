package com.architectcoders.arquitectomarvel.internetconnection

import android.content.Context
import android.net.ConnectivityManager
import com.architectcoders.arquitectomarvel.ui.common.NetworkManager
import com.architectcoders.data.source.NetworkDataSource
import kotlinx.coroutines.flow.collect

class NetworkDataSourceImpl(
    context: Context
) : NetworkDataSource {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override suspend fun registerNetworkManager(listener: (Boolean) -> Unit) {
        NetworkManager(
            connectivityManager,
            networkCallback
        ).isInternetAvailable.collect {
            listener(it)
        }
    }

    override fun unregisterNetworkManager() {
        networkCallback?.let { connectivityManager.unregisterNetworkCallback(it) }
    }
}
