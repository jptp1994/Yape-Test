package com.yape.myapplication.di


import com.yape.data.repository.RecipeRemote
import com.yape.myapplication.core.Constants.BASE_URL
import com.yape.remote.api.RecipeService
import com.yape.remote.api.ServiceFactory
import com.yape.remote.repository.RecipeRemoteImp
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
    fun provideBlogService(): RecipeService {
        return ServiceFactory.create(true,BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRecipeRemote(recipeRemote: RecipeRemoteImp): RecipeRemote {
        return recipeRemote
    }
}
