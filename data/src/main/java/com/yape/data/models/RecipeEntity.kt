package com.yape.data.models

data class RecipeEntity(
    val id:Long,
    val image: String,
    val name: String,
    val description: String,
    val difficult: String,
    val time: String,
    val ingredients: List<RecipeIngredientEntity>,
    val tutorial: String,
    val recipeLocation: RecipeLocationEntity,
    val isBookMarked: Boolean
)
