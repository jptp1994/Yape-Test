package com.yape.data

import com.yape.data.mapper.RecipeMapper
import com.yape.data.source.RecipeDataSourceFactory
import com.yape.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.yape.domain.models.Recipe

class RecipeRepositoryImp @Inject constructor(
    private val dataSourceFactory: RecipeDataSourceFactory,
    private val recipeMapper: RecipeMapper,
) : RecipeRepository {

    override suspend fun gerRecipes(): Flow<List<Recipe>> = flow {
        val isCached = dataSourceFactory.getCacheDataSource().isCached()
        val recipeList =
            dataSourceFactory.getDataStore(isCached).getRecipes().map { recipeEntity ->
                recipeMapper.mapFromEntity(recipeEntity)
            }
        saveRecipes(recipeList)
        emit(recipeList)
    }

    override suspend fun getRecipe(id: Long): Flow<Recipe> = flow {
        var recipeEntity = dataSourceFactory.getCacheDataSource().getRecipe(id)
        if (recipeEntity.image.isEmpty()) {
            recipeEntity = dataSourceFactory.getRemoteDataSource().getRecipe(id)
        }
        emit(
            recipeMapper.mapFromEntity(recipeEntity)
        )
    }

    override suspend fun saveRecipes(listRecipes: List<Recipe>) {
        val characterEntities = listRecipes.map { character ->
            recipeMapper.mapToEntity(character)
        }
        dataSourceFactory.getCacheDataSource().saveRecipes(characterEntities)
    }

    override suspend fun getBookMarkedRecipes(): Flow<List<Recipe>> = flow {
        val characterList = dataSourceFactory.getCacheDataSource().getBookMarkedRecipes()
            .map { characterEntity ->
                recipeMapper.mapFromEntity(characterEntity)
            }
        emit(characterList)
    }

    override suspend fun setRecipeBookmarked(id: Long): Flow<Int> = flow {
        emit(dataSourceFactory.getCacheDataSource().setRecipeBookmarked(id))
    }

    override suspend fun setRecipeUnBookMarked(id: Long): Flow<Int> = flow {
        emit(dataSourceFactory.getCacheDataSource().setRecipeUnBookMarked(id))
    }
}
