package com.yape.domain.interactor

import com.yape.domain.utils.DomainBaseTest
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.domain.repository.RecipeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class RecipeUnBookmarkTestDomain : DomainBaseTest() {
    @Mock
    lateinit var recipeRepository: RecipeRepository

    lateinit var sut: RecipeUnBookmarkUseCase

    @Before
    fun setUp() {
        sut = RecipeUnBookmarkUseCase(recipeRepository)
    }

    @Test
    fun `set bookmark recipe with id should return success with response code`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeId = 1L
            whenever(recipeRepository.setRecipeUnBookMarked(recipeId)) doReturn flow {
                emit(
                    1
                )
            }

            // Act (When)
            val status = sut(recipeId).single()

            // Assert (Then)
            assertEquals(status, 1)
            verify(recipeRepository, times(1)).setRecipeUnBookMarked(recipeId)
        }

    @Test
    fun `set bookmark recipe with id should return fail with response code`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeId = 1L
            whenever(recipeRepository.setRecipeUnBookMarked(recipeId)) doReturn flow {
                emit(
                    0
                )
            }

            // Act (When)
            val status = sut(recipeId).single()

            // Assert (Then)
            assertEquals(status, 0)
            verify(recipeRepository, times(1)).setRecipeUnBookMarked(recipeId)
        }

    @Test
    fun `set bookmark recipe with id should return error result with exception`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeId = 1L
            whenever(recipeRepository.setRecipeUnBookMarked(recipeId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut(recipeId).single()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(IOException::class.java)
                )
            }

            verify(recipeRepository, times(1)).setRecipeUnBookMarked(recipeId)
        }
}
