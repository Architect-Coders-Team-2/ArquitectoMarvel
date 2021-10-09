package com.architectcoders.arquitectomarvel.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.data.source.BiometricPromptDataSource
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.Executor
import javax.inject.Inject

class BiometricPromptDataSourceImpl @Inject constructor(
    @ActivityContext private val context: Context
) : BiometricPromptDataSource {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun canUserUseBiometricAuthentication(): Boolean =
        BiometricManager.from(context).canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.BIOMETRIC_WEAK
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        ) == BiometricManager.BIOMETRIC_SUCCESS

    override fun setBiometricAuthentication(onFail: () -> Unit, onSuccess: () -> Unit) {
        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt =
            BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
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
}
