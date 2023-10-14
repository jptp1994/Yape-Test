package com.yape.cache.mapper

import com.yape.cache.models.RecipeCacheIngredientEntity
import com.yape.data.models.RecipeIngredientEntity
import javax.inject.Inject

class RecipeIngredientCacheMapper @Inject constructor() :
    CacheMapper<RecipeCacheIngredientEntity, RecipeIngredientEntity> {

    override fun mapFromCached(type: RecipeCacheIngredientEntity): RecipeIngredientEntity {
        return RecipeIngredientEntity(name = type.name, image = type.image)
    }

    override fun mapToCached(type: RecipeIngredientEntity): RecipeCacheIngredientEntity {
        return RecipeCacheIngredientEntity(name = type.name, image = type.image)
    }
}
