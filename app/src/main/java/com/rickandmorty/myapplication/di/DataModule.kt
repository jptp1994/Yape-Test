package com.rickandmorty.myapplication.di


import com.rick_and_morty.domain.repository.CharacterRepository
import com.rick_and_morty.domain.repository.LoginRepository
import com.rick_and_morty.domain.repository.SettingsRepository
import com.rickandmorty.data.CharacterRepositoryImp
import com.rickandmorty.data.LoginRepositoryImp
import com.rickandmorty.data.SettingsRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(characterRepository: CharacterRepositoryImp): CharacterRepository =
        characterRepository

    @Provides
    @Singleton
    fun provideLoginRepository(loginRepository: LoginRepositoryImp): LoginRepository =
        loginRepository

    @Provides
    @Singleton
    fun provideSettingRepository(): SettingsRepository =
        SettingsRepositoryImp("RICK_AND_MORTY")
}
