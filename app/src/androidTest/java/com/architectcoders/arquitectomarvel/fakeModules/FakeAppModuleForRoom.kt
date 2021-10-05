package com.architectcoders.arquitectomarvel.fakeModules

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.di.AppModuleForRoomDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModuleForRoomDatabaseProvider::class]
)
class FakeAppModuleForRoom {

    @Provides
    fun databaseProvider(@ApplicationContext appContext: Context): MarvelDatabase =
        Room.inMemoryDatabaseBuilder(appContext, MarvelDatabase::class.java)
            .setQueryExecutor(TestCoroutineDispatcher().asExecutor())
            .build()
}
