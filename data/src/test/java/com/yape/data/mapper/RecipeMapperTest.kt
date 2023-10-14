package com.yape.data.mapper

import com.yape.data.fakes.FakeRecipes
import com.yape.data.utils.DataBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.yape.domain.models.RecipeLocation
import com.yape.data.models.RecipeLocationEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeMapperTest : DataBaseTest() {

    @Mock
    lateinit var locationMapper: RecipeLocationMapper

    @Mock
    lateinit var ingredientMapper: RecipeIngredientMapper


    private lateinit var sut: RecipeMapper

    @Before
    fun setUp() {
        sut = RecipeMapper(locationMapper,ingredientMapper)
    }

    @Test
    fun `map  character entity to character should return converted character`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterFake = FakeRecipes.getRecipes()[0]
            `when`(locationMapper.mapFromEntity(characterFake.recipeLocation)) doReturn RecipeLocation(
                "",
                ""
            )
            // Act (When)
            val recipe = sut.mapFromEntity(characterFake)

            // Assert (Then)
            assertEquals(recipe.id, 1)
            verify(locationMapper, times(1)).mapFromEntity(any())
        }

    @Test
    fun `map character to character entity  should return converted character`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterFake = FakeRecipes.getRecipe()
            `when`(locationMapper.mapToEntity(characterFake.recipeLocation)) doReturn RecipeLocationEntity(
                "",
                ""
            )
            // Act (When)
            val character = sut.mapToEntity(characterFake)

            // Assert (Then)
            assertEquals(character.id, 1)
            verify(locationMapper, times(1)).mapToEntity(any())
        }
}
