package com.yape.cache.mapper

import com.yape.cache.models.RecipeCacheEntity
import com.yape.data.models.RecipeEntity
import javax.inject.Inject

class RecipeCacheMapper @Inject constructor(
    private val recipeLocationCacheMapper: RecipeLocationCacheMapper,
    private val recipeIngredientCacheMapper: RecipeIngredientCacheMapper
) : CacheMapper<RecipeCacheEntity, RecipeEntity> {
    override fun mapFromCached(type: RecipeCacheEntity): RecipeEntity {
        return RecipeEntity(
            id = type.id,
            image = type.image,
            recipeLocation = recipeLocationCacheMapper.mapFromCached(type.recipeLocation),
            name = type.name,
            description= type.description,
            difficult= type.difficult,
            ingredients = type.ingredients.map { recipeIngredientCacheMapper.mapFromCached(it) },
            time= type.time,
            tutorial = type.tutorial,
            isBookMarked = type.isBookMarked
        )
    }

    override fun mapToCached(type: RecipeEntity): RecipeCacheEntity {
        return RecipeCacheEntity(
            id = type.id,
            image = type.image,
            recipeLocation = recipeLocationCacheMapper.mapToCached(type.recipeLocation),
            name = type.name,
            description= type.description,
            difficult= type.difficult,
            ingredients = type.ingredients.map { recipeIngredientCacheMapper.mapToCached(it) },
            time= type.time,
            tutorial = type.tutorial,
            isBookMarked = type.isBookMarked
        )
    }
}
