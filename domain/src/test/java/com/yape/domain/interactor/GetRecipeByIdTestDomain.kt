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
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetRecipeByIdTestDomain : DomainBaseTest() {

    @Mock
    lateinit var recipeRepository: RecipeRepository

    lateinit var sut: GetRecipeByIdUseCase

    @Before
    fun setUp() {
        sut = GetRecipeByIdUseCase(recipeRepository)
    }

    @Test
    fun `get recipe with id should return success result with recipe detail`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeID = 1L
            whenever(recipeRepository.getRecipe(recipeID)) doReturn FakeData.getRecipe()

            // Act (When)
            val recipe = sut(recipeID).single()

            // Assert (Then)
            assertEquals(recipe.id, recipeID.toInt())
            assertEquals(recipe.name, "Rick")
            verify(recipeRepository, times(1)).getRecipe(recipeID)
        }

    @Test
    fun `get recipe with id should return error result with exception`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeID = 1L
            whenever(recipeRepository.getRecipe(recipeID)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut(recipeID).single()
            }catch (exception:IOException){
                // Assert (Then)
                MatcherAssert.assertThat(
                    exception,
                    instanceOf(IOException::class.java)
                )
            }


            verify(recipeRepository, times(1)).getRecipe(recipeID)
        }
}
