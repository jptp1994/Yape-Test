package com.yape.myapplication.di

import android.content.Context
import com.yape.cache.RecipeCacheImp
import com.yape.cache.dao.RecipeDao
import com.yape.cache.database.YapeDatabase
import com.yape.cache.utils.CachePreferencesHelper
import com.yape.data.repository.RecipeCache
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
    fun provideRoomDatabase(@ApplicationContext context: Context): YapeDatabase {
        return YapeDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRecipeDao(roomDatabase: YapeDatabase): RecipeDao {
        return roomDatabase.cachedRecipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeCache(recipeCache: RecipeCacheImp): RecipeCache {
        return recipeCache
    }


    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }
}
