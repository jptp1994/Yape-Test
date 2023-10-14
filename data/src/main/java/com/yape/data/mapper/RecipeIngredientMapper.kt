package com.yape.data.mapper

import com.yape.data.models.RecipeIngredientEntity
import com.yape.domain.models.RecipeIngredient
import javax.inject.Inject

class RecipeIngredientMapper @Inject constructor() :
    Mapper<RecipeIngredientEntity, RecipeIngredient> {

    override fun mapFromEntity(type: RecipeIngredientEntity): RecipeIngredient {
        return RecipeIngredient(
            name = type.name, image = type.image)
    }

    override fun mapToEntity(type: RecipeIngredient): RecipeIngredientEntity {
        return RecipeIngredientEntity(name = type.name, image = type.image)
    }
}
