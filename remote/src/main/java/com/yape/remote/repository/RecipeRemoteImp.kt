package com.yape.remote.repository

import com.yape.data.models.RecipeEntity
import com.yape.data.repository.RecipeRemote
import com.yape.remote.api.RecipeService
import com.yape.remote.mappers.RecipeEntityMapper
import javax.inject.Inject

class RecipeRemoteImp @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeEntityMapper: RecipeEntityMapper
) : RecipeRemote {

    override suspend fun getRecipes(): List<RecipeEntity> {
        return recipeService.getRecipes().recipes.map { recipeModel ->
            recipeEntityMapper.mapFromModel(recipeModel)
        }
    }

    override suspend fun getRecipe(id: Long): RecipeEntity {
        return recipeEntityMapper.mapFromModel(recipeService.getRecipe(id))
    }
}
