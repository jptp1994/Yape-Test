package com.yape.data.source

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.data.fakes.FakeRecipes
import com.yape.data.repository.RecipeRemote
import com.yape.data.utils.DataBaseTest
import junit.framework.TestCase.assertEquals
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
class RecipeRemoteDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var recipeRemote: RecipeRemote

    private lateinit var sut: RecipeRemoteDataSource

    @Before
    fun setUp() {
        sut = RecipeRemoteDataSource(recipeRemote)
    }

    @Test
    fun `get recipes should return recipes from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(recipeRemote.getRecipes()) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(recipeRemote, times(1)).getRecipes()
        }

    @Test
    fun `get recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeRemote.getRecipes()) doAnswer { throw IOException() }

            // Act (When)

            try{
                sut.getRecipes()
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeRemote, times(1)).getRecipes()
        }

    @Test
    fun `get character with character-id should return recipes from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(recipeRemote.getRecipe(characterId)) doReturn FakeRecipes.getRecipes()[0]

            // Act (When)
            val recipes = sut.getRecipe(characterId)

            // Assert (Then)
            assertEquals(recipes.name, "Rick")
            verify(recipeRemote, times(1)).getRecipe(characterId)
        }

    @Test
    fun `get character with character-id should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(recipeRemote.getRecipe(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getRecipe(characterId)
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(recipeRemote, times(1)).getRecipe(characterId)
        }

    @Test
    fun `save recipes should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)

            try{
                sut.saveRecipes(FakeRecipes.getRecipes())
            }catch (exception:UnsupportedOperationException){// Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )


            }


        }

    @Test
    fun `get bookmark recipes should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)

            try{
                sut.getBookMarkedRecipes()
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }


        }

    @Test
    fun `set bookmark character should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.setRecipeBookmarked(1L)
            }catch (exception:UnsupportedOperationException){
                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }

    @Test
    fun `set un-bookmark character should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.setRecipeUnBookMarked(1L)
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }

    @Test
    fun `is cached should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.isCached()
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }
}
