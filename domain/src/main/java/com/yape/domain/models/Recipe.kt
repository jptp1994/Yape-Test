package com.yape.domain.models

data class Recipe(
    val id: Long,
    val image: String,
    val name: String,
    val description: String,
    val difficult: String,
    val time: String,
    val ingredients: List<RecipeIngredient>,
    val tutorial: String,
    val recipeLocation: RecipeLocation,
    val isBookMarked: Boolean
)

/*
val created: String,
val gender: String,
val id: Long,
val image: String,
val recipeLocation: RecipeLocation,
val name: String,
val species: String,
val status: String,
val type: String,
val url: String,*/