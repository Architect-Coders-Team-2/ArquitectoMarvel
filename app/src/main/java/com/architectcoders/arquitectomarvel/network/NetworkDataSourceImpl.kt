package com.architectcoders.arquitectomarvel.network

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.architectcoders.arquitectomarvel.ui.common.NetworkManager
import com.architectcoders.data.source.NetworkDataSource
import kotlinx.coroutines.flow.collect

class NetworkDataSourceImpl(
    private val context: Context
) : NetworkDataSource {

    override suspend fun manageNetworkManager(lifecycle: Any, listener: (Boolean) -> Unit) {
        NetworkManager(
            context,
            lifecycle as Lifecycle
        ).isInternetAvailable.collect {
            listener(it)
        }
    }
}
