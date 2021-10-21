package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.CharacterDetailModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import mockedCharacter
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharacterDetailModule::class]
)
object UninstallCharacterDetailModule

@Module
@InstallIn(SingletonComponent::class)
class FakeCharacterDetailModuleForCharacterId {

    @Singleton
    @Provides
    @Named("characterId")
    fun characterIdProvider(): Int = mockedCharacter.id
}
