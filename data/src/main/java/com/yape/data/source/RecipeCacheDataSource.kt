package com.yape.data.source

import com.yape.data.models.RecipeEntity
import com.yape.data.repository.RecipeCache
import com.yape.data.repository.RecipeDataSource
import javax.inject.Inject

class RecipeCacheDataSource @Inject constructor(
    private val recipeCache: RecipeCache
) : RecipeDataSource {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeCache.getRecipes()
    }

    override suspend fun getRecipe(id: Long): RecipeEntity {
        return recipeCache.getRecipe(id)
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        recipeCache.saveRecipes(listRecipes)
        recipeCache.setLastCacheTime(System.currentTimeMillis())
    }

    override suspend fun getBookMarkedRecipes(): List<RecipeEntity> {
        return recipeCache.getBookMarkedRecipes()
    }

    override suspend fun setRecipeBookmarked(id: Long): Int {
        return recipeCache.setRecipeBookmarked(id)
    }

    override suspend fun setRecipeUnBookMarked(id: Long): Int {
        return recipeCache.setRecipeUnBookMarked(id)
    }

    override suspend fun isCached(): Boolean {
        return recipeCache.isCached()
    }
}
