package com.yape.data.source

import com.yape.data.models.RecipeEntity
import com.yape.data.repository.RecipeDataSource
import com.yape.data.repository.RecipeRemote
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(
    private val recipeRemote: RecipeRemote
) : RecipeDataSource {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeRemote.getRecipes()
    }

    override suspend fun getRecipe(id: Long): RecipeEntity {
        return recipeRemote.getRecipe(id)
    }

    override suspend fun saveRecipes(listRecipes: List<RecipeEntity>) {
        throw UnsupportedOperationException("Save recipe is not supported for RemoteDataSource.")
    }

    override suspend fun getBookMarkedRecipes(): List<RecipeEntity> {
        throw UnsupportedOperationException("Get bookmark recipes is not supported for RemoteDataSource.")
    }

    override suspend fun setRecipeBookmarked(id: Long): Int {
        throw UnsupportedOperationException("Set bookmark recipe is not supported for RemoteDataSource.")
    }

    override suspend fun setRecipeUnBookMarked(id: Long): Int {
        throw UnsupportedOperationException("Set UnBookmark recipes is not supported for RemoteDataSource.")
    }

    override suspend fun isCached(): Boolean {
        throw UnsupportedOperationException("Cache is not supported for RemoteDataSource.")
    }
}
