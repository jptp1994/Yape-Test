package com.yape.cache.fakes

import com.yape.cache.fakes.FakeValueFactory.randomBoolean
import com.yape.cache.fakes.FakeValueFactory.randomLong
import com.yape.cache.fakes.FakeValueFactory.randomString
import com.yape.data.models.RecipeEntity
import com.yape.data.models.RecipeIngredientEntity
import com.yape.data.models.RecipeLocationEntity

object FakeCacheData {

    fun getFakeRecipeEntity(
        size: Int,
        isRandomId: Boolean = true,
        isBookmarked: Boolean = false
    ): List<RecipeEntity> {
        val recipes = mutableListOf<RecipeEntity>()
        repeat(size) {
            recipes.add(createCharacterEntity(isRandomId, isBookmarked))
        }
        return recipes
    }

    private fun createCharacterEntity(isRandomId: Boolean, isBookmarked: Boolean): RecipeEntity {
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
            ingredients = arrayListOf(RecipeIngredientEntity(name = randomString(),image = randomString())),
            isBookMarked = if (isBookmarked) true else randomBoolean()
        )
    }
}
