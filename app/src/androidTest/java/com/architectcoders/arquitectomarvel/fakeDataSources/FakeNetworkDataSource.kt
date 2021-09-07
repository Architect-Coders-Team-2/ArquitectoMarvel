package com.architectcoders.arquitectomarvel.fakeDataSources

import com.architectcoders.data.source.NetworkDataSource
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor() : NetworkDataSource {
    override suspend fun handleNetworkManager(listener: (Boolean) -> Unit) = listener(true)

    override fun unregisterNetworkCallback() = Unit
}
