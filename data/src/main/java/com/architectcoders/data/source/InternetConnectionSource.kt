package com.architectcoders.data.source

interface InternetConnectionSource {
    fun isInternetAvaible(avaible: (Boolean) -> Unit)
}