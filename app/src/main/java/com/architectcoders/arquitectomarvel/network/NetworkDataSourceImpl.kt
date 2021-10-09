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
    private var networkManager: NetworkManager = NetworkManager(context)

    override suspend fun handleNetworkManager(listener: (Boolean) -> Unit) =
        networkManager.isInternetAvailable.collect {
            listener(it)
        }

    override fun clearNetworks() {
        networkManager.clearNetworks()
    }
}
