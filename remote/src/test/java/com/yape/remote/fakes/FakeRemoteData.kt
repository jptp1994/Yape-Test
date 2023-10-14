package com.yape.remote.fakes

import com.yape.remote.fakes.FakeValueFactory.randomString
import com.yape.data.models.RecipeEntity
import com.yape.data.models.RecipeIngredientEntity
import com.yape.data.models.RecipeLocationEntity
import com.yape.remote.fakes.FakeValueFactory.randomLong
import com.yape.remote.models.RecipeIngredientModel
import com.yape.remote.models.RecipeLocationModel
import com.yape.remote.models.RecipeModel
import com.yape.remote.models.RecipeResponseModel

object FakeRemoteData {

    fun getResponse(size: Int, isRandomId: Boolean = true): RecipeResponseModel {
        return RecipeResponseModel(getFakeRecipeModel(size, isRandomId))
    }

    private fun getFakeRecipeModel(size: Int, isRandomId: Boolean): List<RecipeModel> {
        val recipes = mutableListOf<RecipeModel>()
        repeat(size) {
            recipes.add(getRecipeModel(isRandomId))
        }
        return recipes
    }

    fun getRecipeModel(isRandomId: Boolean): RecipeModel {
        return RecipeModel(
            description = randomString(),
            difficult = randomString(),
            id = if (isRandomId) randomLong() else 1,
            image = randomString(),
            recipeLocation = RecipeLocationModel(
                latitude = randomString(),
                longitude = randomString()
            ),
            name = randomString(),
            time = randomString(),
            tutorial = randomString(),
            ingredients = arrayListOf(RecipeIngredientModel(name = randomString(), image = randomString())),
            isBookMarked = false
        )
    }

    fun getRecipeEntity(isRandomId: Boolean): RecipeEntity {
        return RecipeEntity(
            description = randomString(),
            difficult = randomString(),
            id = if (isRandomId) randomLong() else 1,
            image = randomString(),
            recipeLocation = RecipeLocationEntity(
                latitude = randomString(),
                longitude = randomString()
            ),
            name = randomString(),
            time = randomString(),
            tutorial = randomString(),
            ingredients = arrayListOf(RecipeIngredientEntity(name = randomString(), image = randomString())),
            isBookMarked = false
        )
    }
}
