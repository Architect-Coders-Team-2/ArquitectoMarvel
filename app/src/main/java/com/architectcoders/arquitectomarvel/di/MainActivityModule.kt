package com.architectcoders.arquitectomarvel.di

import android.content.Context
import android.content.SharedPreferences
import com.architectcoders.arquitectomarvel.biometric.AuthenticationStateDataSourceImpl
import com.architectcoders.arquitectomarvel.biometric.BiometricPromptDataSourceImpl
import com.architectcoders.arquitectomarvel.ui.common.BIOMETRIC_PREFERENCES
import com.architectcoders.data.source.AuthenticationStateDataSource
import com.architectcoders.data.source.BiometricPromptDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainActivityProvider {

    @ActivityScoped
    @Provides
    fun biometricSharedPreferencesProvider(@ActivityContext context: Context): SharedPreferences =
        context.getSharedPreferences(BIOMETRIC_PREFERENCES, Context.MODE_PRIVATE)
}

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityBinder {

    @ActivityScoped
    @Binds
    abstract fun bindsAuthenticationStateDataSource(
        authenticationStateDataSourceImpl: AuthenticationStateDataSourceImpl
    ): AuthenticationStateDataSource

    @ActivityScoped
    @Binds
    abstract fun bindsBiometricPromptDataSource(
        biometricPromptDataSourceImpl: BiometricPromptDataSourceImpl
    ): BiometricPromptDataSource
}
