package com.yape.ui.viewmodel

import androidx.lifecycle.Observer
import com.yape.ui.fakes.FakePresentationData
import com.yape.ui.utils.PresentationBaseTest
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.domain.interactor.RecipeBookmarkUseCase
import com.yape.domain.interactor.RecipeUnBookmarkUseCase
import com.yape.domain.interactor.GetRecipeByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeDetailViewModelTest : PresentationBaseTest() {

    @Mock
    lateinit var recipesUseCase: GetRecipeByIdUseCase

    @Mock
    lateinit var bookmarkUseCase: RecipeBookmarkUseCase

    @Mock
    lateinit var unBookmarkUseCase: RecipeUnBookmarkUseCase

    @Mock
    private lateinit var observer: Observer<RecipeDetailUIModel>

    private lateinit var sut: RecipeDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = RecipeDetailViewModel(
            dispatcher,
            recipesUseCase,
            bookmarkUseCase,
            unBookmarkUseCase
        )
        sut.getRecipe().observeForever(observer)
    }

    @Test
    fun `get recipe detail with recipe-id should return recipe complete detail from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val recipe = FakePresentationData.getRecipes(1)[0]
            `when`(recipesUseCase(recipeID)).thenReturn(flowOf(recipe))

            // Act (When)
            sut.getRecipeDetail(recipeID)

            // Assert (Then)
            verify(observer).onChanged(RecipeDetailUIModel.Loading)
            verify(observer).onChanged(RecipeDetailUIModel.Success(recipe))
        }

    @Test
    fun `get recipe detail with recipe-id should return error from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val errorMessage = "Internal server error"
            whenever(recipesUseCase(recipeID)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            sut.getRecipeDetail(recipeID)

            // Assert (Then)
            verify(observer).onChanged(RecipeDetailUIModel.Loading)
            verify(observer).onChanged(RecipeDetailUIModel.Error(errorMessage))
        }

    @Test
    fun `set bookmark recipe should return success status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val status = 1
            `when`(bookmarkUseCase(recipeID)).thenReturn(flowOf(status))

            // Act (When)
            sut.setBookmarkRecipe(recipeID)

            // Assert (Then)
            verify(observer).onChanged(
                RecipeDetailUIModel.BookMarkStatus(
                    Bookmark.BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set bookmark recipe should return fail status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val status = 0
            `when`(bookmarkUseCase(recipeID)).thenReturn(flowOf(status))

            // Act (When)
            sut.setBookmarkRecipe(recipeID)

            // Assert (Then)
            verify(observer).onChanged(
                RecipeDetailUIModel.BookMarkStatus(
                    Bookmark.BOOKMARK,
                    false
                )
            )
        }

    @Test
    fun `set un-bookmark recipe should return success status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val status = 1
            `when`(unBookmarkUseCase(recipeID)).thenReturn(flowOf(status))

            // Act (When)
            sut.setUnBookmarkRecipe(recipeID)

            // Assert (Then)
            verify(observer).onChanged(
                RecipeDetailUIModel.BookMarkStatus(
                    Bookmark.UN_BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set un-bookmark recipe should return fail status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val recipeID = 1L
            val status = 0
            `when`(unBookmarkUseCase(recipeID)).thenReturn(flowOf(status))

            // Act (When)
            sut.setUnBookmarkRecipe(recipeID)

            // Assert (Then)
            verify(observer).onChanged(
                RecipeDetailUIModel.BookMarkStatus(
                    Bookmark.UN_BOOKMARK,
                    false
                )
            )
        }

    @After
    fun tearDown() {
        sut.onCleared()
    }
}
