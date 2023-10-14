package com.yape.remote.mappers

import com.yape.data.models.RecipeIngredientEntity
import com.yape.remote.models.RecipeIngredientModel
import javax.inject.Inject

class RecipeIngredientEntityMapper @Inject constructor() :
    EntityMapper<RecipeIngredientModel, RecipeIngredientEntity> {

    override fun mapFromModel(model: RecipeIngredientModel): RecipeIngredientEntity {
        return RecipeIngredientEntity(name = model.name, image = model.image)
    }
}
