package com.architectcoders.arquitectomarvel.internetconnection

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import com.architectcoders.arquitectomarvel.ui.common.showIfInternetIsAvailable
import com.architectcoders.data.source.InternetConnectionSource

class InternetConnectioChecketImpl(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope
) : InternetConnectionSource {

    override fun isInternetAvaible(avaible: (Boolean) -> Unit) {
        context.showIfInternetIsAvailable(
            lifecycle,
            lifecycleCoroutineScope
        ) {
            avaible(it)
        }
    }
}