package com.architectcoders.arquitectomarvel.ui.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import timber.log.Timber

class NetworkMonitor

constructor(private val application: Application) {

    private var isNetworkConnected: Boolean = false

    @SuppressLint("NewApi")
    fun startNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        Timber.d("NetworkMonitor: starting")

        /**Check if version code is greater than API 24*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(
                builder.build(), networkCallback
            )
        }
    }

    @SuppressLint("NewApi")
    fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())

        Timber.d("NetworkMonitor: stopped")
    }


    private val networkCallback = @SuppressLint("NewApi")
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            isNetworkConnected = true
            Timber.d("NetworkMonitor: network connected")
            /* TODO: Check if really has internet */
        }

        override fun onLost(network: Network) {
            isNetworkConnected = false
            Timber.d("NetworkMonitor: network disconnected")
        }
    }


}