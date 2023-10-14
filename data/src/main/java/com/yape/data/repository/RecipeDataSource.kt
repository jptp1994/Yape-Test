package com.yape.data.repository

import com.yape.data.models.RecipeEntity

interface RecipeDataSource {
    // Remote and cache
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(id: Long): RecipeEntity

    // Cache
    suspend fun saveRecipes(listRecipes: List<RecipeEntity>)
    suspend fun getBookMarkedRecipes(): List<RecipeEntity>
    suspend fun setRecipeBookmarked(id: Long): Int
    suspend fun setRecipeUnBookMarked(id: Long): Int
    suspend fun isCached(): Boolean
}
