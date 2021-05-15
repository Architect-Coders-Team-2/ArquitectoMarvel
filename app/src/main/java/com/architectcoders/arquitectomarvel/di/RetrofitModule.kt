package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.remote.ApiService
import com.architectcoders.arquitectomarvel.data.remote.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService =
        retrofit.build().create(ApiService::class.java)
}