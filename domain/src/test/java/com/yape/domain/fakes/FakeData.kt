package com.yape.domain.fakes

import kotlinx.coroutines.flow.Flow
import com.yape.domain.models.Recipe
import com.yape.domain.models.RecipeIngredient
import com.yape.domain.models.RecipeLocation
import com.yape.domain.models.SettingType
import com.yape.domain.models.Settings
import kotlinx.coroutines.flow.flow

object FakeData {
    fun getRecipes(): Flow<List<Recipe>> = flow {
        val recipes = listOf(

            Recipe(
                id= 1,
                description = "Male",
                name= "jean",
                image= "https://dummyurl.png",
                recipeLocation = RecipeLocation("151511.20", "11394949494.09"),
                difficult= "Hard",
                time ="15 minutes",
                tutorial = "cook a potato",
                ingredients = arrayListOf(RecipeIngredient("rice","https://dummyurl.png")),
                isBookMarked= false
            ),
            Recipe(
                id=2,
                description = "Female",
                name= "maria",
                image= "https://dummyurl.png",
                recipeLocation = RecipeLocation("151511.20", "11394949494.09"),
                difficult= "Hard",
                time ="15 minutes",
                tutorial = "cook rice",
                ingredients = arrayListOf(RecipeIngredient("rice","https://dummyurl.png")),
                isBookMarked= false
            )
        )
        emit(recipes)
    }

    fun getRecipe(): Flow<Recipe> = flow {
        emit(
            Recipe(
                id= 1,
                description = "Male",
                name= "jean",
                image= "https://dummyurl.png",
                recipeLocation = RecipeLocation("151511.20", "11394949494.09"),
                difficult= "Hard",
                time ="15 minutes",
                tutorial = "cook a potato",
                ingredients = arrayListOf(RecipeIngredient("rice","https://dummyurl.png")),
                isBookMarked= false
            )
        )
    }

    fun getSettings(isNightMode: Boolean): Flow<List<Settings>> = flow {
        val settings = listOf(
            Settings(1, SettingType.SWITCH, "Theme mode", "", isNightMode),
            Settings(2, SettingType.EMPTY, "Clear cache", ""),
            Settings(2, SettingType.TEXT, "App version", "1.0")
        )
        emit(settings)
    }
}
