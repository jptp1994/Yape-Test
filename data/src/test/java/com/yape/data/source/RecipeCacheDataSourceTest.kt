package com.yape.data.source

import com.yape.data.fakes.FakeRecipes
import com.yape.data.utils.DataBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.data.repository.RecipeCache
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeCacheDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var recipeCache: RecipeCache

    private lateinit var sut: RecipeCacheDataSource

    @Before
    fun setUp() {
        sut = RecipeCacheDataSource(recipeCache)
    }

    @Test
    fun `get recipes should return recipes from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(recipeCache.getRecipes()) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(recipeCache, times(1)).getRecipes()
        }

    @Test
    fun `get recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeCache.getRecipes()) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getRecipes()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeCache, times(1)).getRecipes()
        }

    @Test
    fun `get character with character-id should return recipes from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(recipeCache.getRecipe(characterId)) doReturn FakeRecipes.getRecipes()[0]

            // Act (When)
            val recipes = sut.getRecipe(characterId)

            // Assert (Then)
            assertEquals(recipes.name, "Rick")
            verify(recipeCache, times(1)).getRecipe(characterId)
        }

    @Test
    fun `get character with character-id should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(recipeCache.getRecipe(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getRecipe(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeCache, times(1)).getRecipe(characterId)
        }

    @Test
    fun `save character passed character list should save character into local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipes = FakeRecipes.getRecipes()
            // Act (When)
            sut.saveRecipes(recipes)

            // Assert (Then)
            verify(recipeCache, times(1)).saveRecipes(recipes)
            verify(recipeCache, times(1)).setLastCacheTime(any())
        }

    @Test
    fun `save character passed character list should return error failed to save last time`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipes = FakeRecipes.getRecipes()
            whenever(recipeCache.saveRecipes(recipes)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.saveRecipes(recipes)
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeCache, times(1)).saveRecipes(recipes)
            verify(recipeCache, times(0)).setLastCacheTime(any())
        }

    @Test
    fun `get bookmark recipes should return recipes from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(recipeCache.getBookMarkedRecipes()) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.getBookMarkedRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(recipeCache, times(1)).getBookMarkedRecipes()
        }

    @Test
    fun `get bookmark recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeCache.getBookMarkedRecipes()) doAnswer { throw IOException() }


            // Act (When)
            try{
                sut.getBookMarkedRecipes()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }


            verify(recipeCache, times(1)).getBookMarkedRecipes()
        }

    @Test
    fun `set bookmark recipes should return success status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 1
            `when`(recipeCache.setRecipeBookmarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setRecipeBookmarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(recipeCache, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set bookmark recipes should return fail status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 0
            `when`(recipeCache.setRecipeBookmarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setRecipeBookmarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(recipeCache, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set bookmark recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(recipeCache.setRecipeBookmarked(characterId)) doAnswer { throw IOException() }

            // Act (When)

            try{
                sut.setRecipeBookmarked(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeCache, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set un-bookmark recipes should return success status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 1
            `when`(recipeCache.setRecipeUnBookMarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setRecipeUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(recipeCache, times(1)).setRecipeUnBookMarked(characterId)
        }

    @Test
    fun `set un-bookmark recipes should return fail status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 0
            `when`(recipeCache.setRecipeUnBookMarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setRecipeUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(recipeCache, times(1)).setRecipeUnBookMarked(characterId)
        }

    @Test
    fun `set in-bookmark recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(recipeCache.setRecipeUnBookMarked(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.setRecipeUnBookMarked(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeCache, times(1)).setRecipeUnBookMarked(characterId)
        }

    @Test
    fun `is cached should return true`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(recipeCache.isCached()) doReturn true

            // Act (When)
            val resultStatus = sut.isCached()

            // Assert (Then)
            assertTrue(resultStatus)
            verify(recipeCache, times(1)).isCached()
        }

    @Test
    fun `is cached should return false`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(recipeCache.isCached()) doReturn false

            // Act (When)
            val resultStatus = sut.isCached()

            // Assert (Then)
            assertFalse(resultStatus)
            verify(recipeCache, times(1)).isCached()
        }
}
