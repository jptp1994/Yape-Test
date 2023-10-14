package com.yape.data.mapper

import com.yape.data.models.RecipeEntity
import javax.inject.Inject
import com.yape.domain.models.Recipe

class RecipeMapper @Inject constructor(
    private val locationMapper: RecipeLocationMapper,
    private val ingredientMapper: RecipeIngredientMapper
) : Mapper<RecipeEntity, Recipe> {

    override fun mapFromEntity(type: RecipeEntity): Recipe {
        return Recipe(
            id = type.id,
            image = type.image,
            recipeLocation = locationMapper.mapFromEntity(type.recipeLocation),
            name = type.name,
            description= type.description,
            difficult= type.difficult,
            ingredients = type.ingredients.map { ingredientMapper.mapFromEntity(it) },
            time= type.time,
            tutorial = type.tutorial,
            isBookMarked = type.isBookMarked
        )
    }

    override fun mapToEntity(type: Recipe): RecipeEntity {
        return RecipeEntity(
            id = type.id,
            image = type.image,
            recipeLocation = locationMapper.mapToEntity(type.recipeLocation),
            name = type.name,
            description= type.description,
            difficult= type.difficult,
            ingredients = type.ingredients.map { ingredientMapper.mapToEntity(it) },
            time= type.time,
            tutorial = type.tutorial,
            isBookMarked = type.isBookMarked
        )
    }
}
