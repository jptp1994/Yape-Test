package com.yape.data.source

import com.yape.data.repository.RecipeCache
import com.yape.data.repository.RecipeDataSource
import javax.inject.Inject

open class RecipeDataSourceFactory @Inject constructor(
    private val recipeCache: RecipeCache,
    private val cacheDataSource: RecipeCacheDataSource,
    private val remoteDataSource: RecipeRemoteDataSource
) {

    open suspend fun getDataStore(isCached: Boolean): RecipeDataSource {
        return if (isCached && !recipeCache.isExpired()) {
            return getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): RecipeDataSource {
        return remoteDataSource
    }

    fun getCacheDataSource(): RecipeDataSource {
        return cacheDataSource
    }
}
