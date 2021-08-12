package com.architectcoders.data.source

interface AuthenticationStateDataSource {
    fun checkAuthenticationState()
    fun checkIfUserIsAlreadyAuthenticated(): Boolean
    fun saveAuthenticationState(isAuthenticated: Boolean)
}
