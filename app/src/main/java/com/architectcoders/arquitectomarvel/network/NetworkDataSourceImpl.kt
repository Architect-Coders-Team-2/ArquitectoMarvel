package com.architectcoders.arquitectomarvel.network

import android.content.Context
import com.architectcoders.arquitectomarvel.ui.common.NetworkManager
import com.architectcoders.data.source.NetworkDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkDataSource {

    lateinit var networkManager: NetworkManager

    override suspend fun handleNetworkManager(listener: (Boolean) -> Unit) {
        networkManager = NetworkManager(context)
        networkManager.isInternetAvailable.collect {
            listener(it)
        }
    }

    override fun unregisterNetworkCallback() {
        networkManager.unregisterNetworkCallback()
    }
}
