package com.yape.cache

import com.yape.cache.dao.RecipeDao
import com.yape.cache.mapper.RecipeCacheMapper
import com.yape.cache.utils.CachePreferencesHelper
import com.yape.data.models.RecipeEntity
import com.yape.data.repository.RecipeCache
import javax.inject.Inject

class RecipeCacheImp @Inject constructor(
    private val recipeDao: RecipeDao,
    private val recipeCacheMapper: RecipeCacheMapper,
    private val preferencesHelper: CachePreferencesHelper
) : RecipeCache {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeDao.getRecipes().map { cacheRecipe ->
            recipeCacheMapper.mapFromCached(cacheRecipe)
        }
    }

    override suspend fun getRecipe(id: Long): RecipeEntity {
        return recipeCacheMapper.mapFromCached(recipeDao.getRecipe(id))
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        recipeDao.addRecipe(
            *listRecipes.map {
                recipeCacheMapper.mapToCached(it)
            }.toTypedArray()
        )
    }

    override suspend fun getBookMarkedRecipes(): List<RecipeEntity> {
        return recipeDao.getBookMarkedRecipe().map { cacheRecipes ->
            recipeCacheMapper.mapFromCached(cacheRecipes)
        }
    }

    override suspend fun setRecipeBookmarked(id: Long): Int {
        return recipeDao.bookmarkRecipe(id)
    }

    override suspend fun setRecipeUnBookMarked(id: Long): Int {
        return recipeDao.unBookmarkRecipe(id)
    }

    override suspend fun isCached(): Boolean {
        return recipeDao.getRecipes().isNotEmpty()
    }

    override suspend fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}
