package com.architectcoders.data.repository

import com.architectcoders.data.source.InternetConnectionSource

class InternetAvaibleRepo(
    private val internetConnectionSource: InternetConnectionSource
) {

    fun checkInternet(avaible: (Boolean) -> Unit) {
        return internetConnectionSource.isInternetAvaible(avaible)
    }
}