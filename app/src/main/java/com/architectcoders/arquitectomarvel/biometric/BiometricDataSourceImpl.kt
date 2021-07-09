package com.architectcoders.arquitectomarvel.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.ui.common.AUTHENTICATION_TIMESTAMP
import com.architectcoders.arquitectomarvel.ui.common.BIOMETRIC_PREFERENCES
import com.architectcoders.arquitectomarvel.ui.common.IS_AUTHENTICATED
import com.architectcoders.data.source.BiometricDataSource
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class BiometricDataSourceImpl(
    private val context: Context
) : BiometricDataSource {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val sharedPreferences =
        context.getSharedPreferences(BIOMETRIC_PREFERENCES, Context.MODE_PRIVATE)

    init {
        checkAuthenticationState()
    }

    private fun checkAuthenticationState() {
        val cacheTimeOut = TimeUnit.DAYS.toMillis(1)
        val lastTimeStamp = sharedPreferences.getLong(AUTHENTICATION_TIMESTAMP, -1L)
        if (lastTimeStamp != -1L && System.currentTimeMillis() - lastTimeStamp >= cacheTimeOut) {
            saveAuthenticationState(false)
        }
    }

    override fun setBiometricAuthentication(onFail: () -> Unit, onSuccess: () -> Unit) {
        if (sharedPreferences.getBoolean(IS_AUTHENTICATED, false) || !userCanAuthenticate()) {
            onSuccess()
            return
        }
        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt =
            BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        saveAuthenticationState(true)
                        onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        onFail()
                    }
                })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.check_favorites))
            .setSubtitle(context.getString(R.string.biometric_device_credentials))
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        or BiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun userCanAuthenticate(): Boolean =
        BiometricManager.from(context).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.BIOMETRIC_WEAK
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == BiometricManager.BIOMETRIC_SUCCESS

    private fun saveAuthenticationState(isAuthenticated: Boolean) =
        with(sharedPreferences.edit()) {
            putBoolean(IS_AUTHENTICATED, isAuthenticated)
            putLong(AUTHENTICATION_TIMESTAMP, System.currentTimeMillis())
            apply()
        }
}
