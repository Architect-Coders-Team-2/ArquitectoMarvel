package com.architectcoders.arquitectomarvel.internetconnection

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.architectcoders.arquitectomarvel.ui.common.InternetConnectionManager
import com.architectcoders.data.source.InternetConnectionSource

class InternetConnectioChecketImpl(
    private val context: Context,
    private val lifecycle: Lifecycle
) : InternetConnectionSource {

    override fun isInternetAvaible(avaible: (Boolean) -> Unit) {
        InternetConnectionManager(context, lifecycle) { avaible(it) }
    }
}