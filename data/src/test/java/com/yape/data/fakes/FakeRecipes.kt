package com.yape.data.fakes

import com.yape.domain.models.Recipe
import com.yape.domain.models.RecipeLocation
import com.yape.data.models.RecipeEntity
import com.yape.data.models.RecipeIngredientEntity
import com.yape.data.models.RecipeLocationEntity
import com.yape.domain.models.RecipeIngredient

object FakeRecipes {
    fun getRecipes(): List<RecipeEntity> = listOf(
        RecipeEntity(
            description = "01/02/2021",
            name= "Male",
            id= 1,
            image = "https://dummyurl.png",
            recipeLocation = RecipeLocationEntity("Earth", "https://dummy.url"),
            tutorial = "Rick",
            time = "15 minutes",
            difficult = "hard",
            ingredients = arrayListOf(RecipeIngredientEntity(name = "jean", image = "https://dummyurl.png")),
            isBookMarked= false
        ),
        RecipeEntity(
            description = "01/02/2021",
            name= "Male",
            id= 2,
            image = "https://dummyurl.png",
            recipeLocation = RecipeLocationEntity("Earth", "https://dummy.url"),
            tutorial = "Rick",
            time = "15 minutes",
            difficult = "hard",
            ingredients = arrayListOf(RecipeIngredientEntity(name = "jean", image = "https://dummyurl.png")),
            isBookMarked= false
        )
    )

    fun getRecipe(): Recipe =
        Recipe(
            description = "01/02/2021",
            name= "Male",
            id= 1,
            image = "https://dummyurl.png",
            recipeLocation = RecipeLocation("Earth", "https://dummy.url"),
            tutorial = "Rick",
            time = "15 minutes",
            difficult = "hard",
            ingredients = arrayListOf(RecipeIngredient(name = "jean", image = "https://dummyurl.png")),
            isBookMarked= false
        )
}
