package com.yape.remote.mappers

import com.yape.remote.models.RecipeModel
import com.yape.data.models.RecipeEntity
import javax.inject.Inject

class RecipeEntityMapper @Inject constructor(
    private val recipeLocationEntityMapper: RecipeLocationEntityMapper,
    private val recipeIngredientEntityMapper: RecipeIngredientEntityMapper,

    ) : EntityMapper<RecipeModel, RecipeEntity> {
    override fun mapFromModel(model: RecipeModel): RecipeEntity {
        return RecipeEntity(
            id = model.id,
            image = model.image,
            recipeLocation = recipeLocationEntityMapper.mapFromModel(model.recipeLocation),
            name = model.name,
            description= model.description,
            difficult= model.difficult,
            ingredients = model.ingredients.map { recipeIngredientEntityMapper.mapFromModel(it) },
            time= model.time,
            tutorial = model.tutorial,
            isBookMarked = model.isBookMarked
        )
    }
}
