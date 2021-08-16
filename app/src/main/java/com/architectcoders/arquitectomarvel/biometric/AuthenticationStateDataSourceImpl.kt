package com.architectcoders.arquitectomarvel.biometric

import android.content.SharedPreferences
import com.architectcoders.arquitectomarvel.ui.common.AUTHENTICATION_TIMESTAMP
import com.architectcoders.arquitectomarvel.ui.common.IS_AUTHENTICATED
import com.architectcoders.data.source.AuthenticationStateDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthenticationStateDataSourceImpl @Inject constructor(
    private val biometricSharedPreferences: SharedPreferences
) : AuthenticationStateDataSource {

    override fun checkAuthenticationState() {
        val cacheTimeOut = TimeUnit.DAYS.toMillis(1)
        val lastTimeStamp = biometricSharedPreferences.getLong(AUTHENTICATION_TIMESTAMP, -1L)
        if (lastTimeStamp != -1L && System.currentTimeMillis() - lastTimeStamp >= cacheTimeOut) {
            saveAuthenticationState(false)
        }
    }

    override fun checkIfUserIsAlreadyAuthenticated(): Boolean =
        biometricSharedPreferences.getBoolean(IS_AUTHENTICATED, false)

    override fun saveAuthenticationState(isAuthenticated: Boolean) {
        with(biometricSharedPreferences.edit()) {
            putBoolean(IS_AUTHENTICATED, isAuthenticated)
            putLong(AUTHENTICATION_TIMESTAMP, System.currentTimeMillis())
            apply()
        }
    }
}
