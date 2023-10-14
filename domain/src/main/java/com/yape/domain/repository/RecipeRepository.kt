package com.yape.domain.repository

import com.yape.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    // Remote and cache
    suspend fun gerRecipes(): Flow<List<Recipe>>
    suspend fun getRecipe(id: Long): Flow<Recipe>

    // Cache
    suspend fun saveRecipes(listRecipes: List<Recipe>)
    suspend fun getBookMarkedRecipes(): Flow<List<Recipe>>
    suspend fun setRecipeBookmarked(id: Long): Flow<Int>
    suspend fun setRecipeUnBookMarked(id: Long): Flow<Int>
}
