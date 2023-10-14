package com.yape.remote.repository

import com.yape.remote.fakes.FakeRemoteData
import com.yape.remote.utils.RemoteBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.remote.api.RecipeService
import com.yape.remote.mappers.RecipeEntityMapper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeRemoteImpTest : RemoteBaseTest() {

    @Mock
    lateinit var recipeService: RecipeService

    @Mock
    lateinit var mapper: RecipeEntityMapper

    private lateinit var sut: RecipeRemoteImp

    @Before
    fun setUp() {
        sut = RecipeRemoteImp(recipeService, mapper)
    }

    @Test
    fun `get recipes should return response with list size 7 from remote server`() =
        dispatcher.runTest {
            // Arrange (Given)
            val response = FakeRemoteData.getResponse(7)
            `when`(recipeService.getRecipes()) doReturn response

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 7)
            verify(mapper, times(7)).mapFromModel(any())
        }

    @Test
    fun `get recipes should return response with empty recipe list from remote server`() =
        dispatcher.runTest {
            // Arrange (Given)
            val response = FakeRemoteData.getResponse(0)
            `when`(recipeService.getRecipes()) doReturn response

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 0)
            verify(mapper, times(0)).mapFromModel(any())
        }

    @Test
    fun `get recipes should return error from remote server`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeService.getRecipes()) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getRecipes()

            }
            catch(exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }



            verify(recipeService, times(1)).getRecipes()
        }

    @Test
    fun `get character should return response from remote server`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val response = FakeRemoteData.getRecipeModel(false)
            `when`(recipeService.getRecipe(characterId)) doReturn response
            `when`(mapper.mapFromModel(response)) doReturn FakeRemoteData.getRecipeEntity(false)
            // Act (When)
            val character = sut.getRecipe(characterId)

            // Assert (Then)
            assertEquals(character.id, 1)
            assertTrue(character.name.isNotEmpty())
            verify(recipeService, times(1)).getRecipe(characterId)
            verify(mapper, times(1)).mapFromModel(any())
        }

    @Test
    fun `get character should return error response from remote server`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(recipeService.getRecipe(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getRecipe(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeService, times(1)).getRecipe(characterId)
        }
}
