package com.yape.domain.interactor

import com.yape.domain.fakes.FakeData
import com.yape.domain.utils.DomainBaseTest
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.domain.repository.RecipeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetBookmarkRecipeListTestDomain : DomainBaseTest() {

    @Mock
    lateinit var recipeRepository: RecipeRepository

    lateinit var sut: GetBookmarkRecipeListUseCase

    @Before
    fun setUp() {
        sut = GetBookmarkRecipeListUseCase(recipeRepository)
    }

    @Test
    fun `get bookmark recipe should return success result with bookmark recipe list`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeRepository.getBookMarkedRecipes()) doReturn FakeData.getRecipes()

            // Act (When)
            val recipes = sut(Unit).single()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(recipeRepository, times(1)).getBookMarkedRecipes()
        }

    @Test
    fun `get bookmark recipes should return error result with exception`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(recipeRepository.getBookMarkedRecipes()) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut(Unit).single()
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(IOException::class.java)
                )
            }


            verify(recipeRepository, times(1)).getBookMarkedRecipes()
        }
}
