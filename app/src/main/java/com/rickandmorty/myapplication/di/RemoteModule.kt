package com.rickandmorty.myapplication.di


import com.rickandmorty.data.repository.CharacterRemote
import com.rickandmorty.myapplication.core.Constants.BASE_URL
import com.rickandmorty.remote.api.CharacterService
import com.rickandmorty.remote.api.ServiceFactory
import com.rickandmorty.remote.repository.CharacterRemoteImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideBlogService(): CharacterService {
        return ServiceFactory.create(true,BASE_URL)
    }

    @Provides
    @Singleton
    fun provideCharacterRemote(characterRemote: CharacterRemoteImp): CharacterRemote {
        return characterRemote
    }
}
