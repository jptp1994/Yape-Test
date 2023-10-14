package com.yape.data.repository

import com.yape.data.models.RecipeEntity

interface RecipeRemote {
    suspend fun getRecipes(): List<RecipeEntity>
    suspend fun getRecipe(id: Long): RecipeEntity
}
