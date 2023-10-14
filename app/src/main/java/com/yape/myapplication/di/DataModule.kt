package com.yape.myapplication.di


import com.yape.domain.repository.RecipeRepository
import com.yape.domain.repository.SettingsRepository
import com.yape.data.RecipeRepositoryImp
import com.yape.data.SettingsRepositoryImp
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
    fun provideRecipeRepository(recipeRepository: RecipeRepositoryImp): RecipeRepository =
        recipeRepository

    @Provides
    @Singleton
    fun provideSettingRepository(): SettingsRepository =
        SettingsRepositoryImp("YAPE")
}
