package com.yape.remote.models

import com.squareup.moshi.Json

data class RecipeModel(
    val id:Long,
    val image: String,
    val name: String,
    val description: String,
    val difficult: String,
    val time: String,
    val ingredients: List<RecipeIngredientModel>,
    val tutorial: String,
    @field:Json(name = "location")
    val recipeLocation: RecipeLocationModel,
    val isBookMarked: Boolean
)
