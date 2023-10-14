package com.yape.data.repository

import com.yape.data.models.RecipeEntity

interface RecipeCache {
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(id: Long): RecipeEntity
    suspend fun saveRecipes(listRecipes: List<RecipeEntity>)
    suspend fun getBookMarkedRecipes(): List<RecipeEntity>
    suspend fun setRecipeBookmarked(id: Long): Int
    suspend fun setRecipeUnBookMarked(id: Long): Int
    suspend fun isCached(): Boolean
    suspend fun setLastCacheTime(lastCache: Long)
    suspend fun isExpired(): Boolean
}
