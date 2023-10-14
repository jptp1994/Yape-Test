package com.yape.ui.fakes


import com.yape.domain.models.Recipe
import com.yape.domain.models.RecipeIngredient
import com.yape.ui.fakes.FakeValueFactory.randomBoolean
import com.yape.ui.fakes.FakeValueFactory.randomInt
import com.yape.ui.fakes.FakeValueFactory.randomString
import com.yape.domain.models.RecipeLocation
import com.yape.domain.models.SettingType
import com.yape.domain.models.Settings
import com.yape.ui.fakes.FakeValueFactory.randomLong

object FakePresentationData {

    fun getRecipes(
        size: Int,
        isRandomId: Boolean = true,
        isBookmarked: Boolean = false
    ): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        repeat(size) {
            recipes.add(createRecipe(isRandomId, isBookmarked))
        }
        return recipes
    }

    fun getSettings(size: Int): List<Settings> {
        val settings = mutableListOf<Settings>()
        repeat(size) {
            settings.add(createSetting())
        }
        return settings
    }

    private fun createRecipe(isRandomId: Boolean, isBookmarked: Boolean): Recipe {
        return Recipe(
            description = randomString(),
            difficult = randomString(),
            id = if (isRandomId) randomLong() else 1,
            image = randomString(),
            recipeLocation = RecipeLocation(
                latitude = randomString(),
                longitude = randomString()
            ),
            name = randomString(),
            tutorial = randomString(),
            time = randomString(),
            ingredients = arrayListOf(RecipeIngredient(name = randomString(), image = randomString())),
            isBookMarked = if (isBookmarked) true else randomBoolean()
        )
    }

    private fun createSetting(): Settings {
        return Settings(
            id = randomInt(),
            type = SettingType.SWITCH,
            settingLabel = randomString(),
            settingValue = randomString()
        )
    }
}
