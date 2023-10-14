package com.yape.data.mapper


import com.yape.data.fakes.FakeRecipes
import com.yape.data.utils.DataBaseTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeLocationMapperTest : DataBaseTest() {

    private lateinit var sut: RecipeLocationMapper

    @Before
    fun setUp() {
        sut = RecipeLocationMapper()
    }

    @Test
    fun `map  location entity to location should return converted location`() =
        dispatcher.runTest {
            // Arrange (Given)
            val locationFake = FakeRecipes.getRecipes()[0].recipeLocation

            // Act (When)
            val locationMapper = sut.mapFromEntity(locationFake)

            // Assert (Then)
            assertEquals(locationMapper.latitude, "Earth")
        }

    @Test
    fun `map  location to location entity should return converted location`() =
        dispatcher.runTest {
            // Arrange (Given)
            val locationFake = FakeRecipes.getRecipe().recipeLocation

            // Act (When)
            val locationMapper = sut.mapToEntity(locationFake)

            // Assert (Then)
            assertEquals(locationMapper.latitude, "Earth")
        }
}
