package com.architectcoders.arquitectomarvel.di

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDao
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.server.ApiService
import com.architectcoders.arquitectomarvel.ui.common.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModuleForRoom {

    @Singleton
    @Provides
    fun databaseProvider(@ApplicationContext appContext: Context): MarvelDatabase =
        Room.databaseBuilder(
            appContext,
            MarvelDatabase::class.java,
            "marvelDb"
        ).build()

    @Singleton
    @Provides
    fun marvelDaoProvider(marvelDatabase: MarvelDatabase): MarvelDao = marvelDatabase.marvelDao
}

@Module
@InstallIn(SingletonComponent::class)
class AppModuleForRetrofit {

    @Singleton
    @Provides
    fun httpLoggingInterceptorProvider(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun okHttpClientProvider(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())

    @Singleton
    @Provides
    fun marvelApiServiceProvider(retrofitBuilder: Retrofit.Builder, @Named("baseUrl") baseUrl: String): ApiService =
        retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(ApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
class AppModuleForEndPointBaseUrl {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider(): String = BASE_URL
}
