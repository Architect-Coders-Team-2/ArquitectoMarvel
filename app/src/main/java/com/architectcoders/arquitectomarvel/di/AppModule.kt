package com.architectcoders.arquitectomarvel.di

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDao
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.server.ApiService
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.arquitectomarvel.ui.common.BASE_URL
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.NetworkDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Room
    @Singleton
    @Provides
    fun databaseProvider(@ApplicationContext context: Context): MarvelDatabase =
        Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            "result_db"
        ).build()


    @Singleton
    @Provides
    fun provideChannelDao(appDatabase: MarvelDatabase): MarvelDao {
        return appDatabase.marvelDao
    }

    // Retrofit
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

    // MarvelRepo
    @Provides
    @Singleton
    fun provideMarvelRepo(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MarvelRepository = MarvelRepository(
        remoteDataSource, localDataSource
    )

    // NetworkRepo
    @Provides
    @Singleton
    fun provideNetworkDataSource(
        @ApplicationContext appContext: Context
    ): NetworkDataSource = NetworkDataSourceImpl(
        appContext
    )

    @Provides
    @Singleton
    fun provideNetworkRepo(
        networkDataSource: NetworkDataSource
    ): NetworkRepository = NetworkRepository(
        networkDataSource
    )
}