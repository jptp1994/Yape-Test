package com.rickandmorty.myapplication.di

import android.content.Context
import com.rickandmorty.cache.CharacterCacheImp
import com.rickandmorty.cache.LoginCacheImp
import com.rickandmorty.cache.dao.CharacterDao
import com.rickandmorty.cache.dao.LoginDao
import com.rickandmorty.cache.database.CharactersDatabase
import com.rickandmorty.cache.utils.CachePreferencesHelper
import com.rickandmorty.data.repository.CharacterCache
import com.rickandmorty.data.repository.LoginCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CharactersDatabase {
        return CharactersDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(charactersDatabase: CharactersDatabase): CharacterDao {
        return charactersDatabase.cachedCharacterDao()
    }


    @Provides
    @Singleton
    fun provideLoginDao(charactersDatabase: CharactersDatabase): LoginDao {
        return charactersDatabase.cachedLoginDao()
    }

    @Provides
    @Singleton
    fun provideCharacterCache(characterCache: CharacterCacheImp): CharacterCache {
        return characterCache
    }

    @Provides
    @Singleton
    fun provideLoginCache(loginCache: LoginCacheImp): LoginCache {
        return loginCache
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }
}
