package com.yape.ui.viewmodel

import androidx.lifecycle.Observer
import com.yape.ui.fakes.FakePresentationData
import com.yape.ui.utils.PresentationBaseTest
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.domain.interactor.GetBookmarkRecipeListUseCase
import com.yape.domain.interactor.GetRecipeListUseCase
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
class RecipeListViewModelTest : PresentationBaseTest() {

    @Mock
    lateinit var recipesUseCase: GetRecipeListUseCase

    @Mock
    lateinit var bookmarkUseCase: GetBookmarkRecipeListUseCase

    @Mock
    private lateinit var observer: Observer<RecipeUIModel>

    private lateinit var sut: RecipeListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = RecipeListViewModel(dispatcher, recipesUseCase, bookmarkUseCase)
        sut.getRecipe().observeForever(observer)
    }

    @Test
    fun `get recipes should return recipes list from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = false
            val recipes = FakePresentationData.getRecipes(7)
            `when`(recipesUseCase(Unit)).thenReturn(flowOf(recipes))

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Success(recipes))
        }

    @Test
    fun `get recipes should return empty recipes list from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = false
            val recipes = FakePresentationData.getRecipes(0)
            `when`(recipesUseCase(Unit)).thenReturn(flowOf(recipes))

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Success(recipes))
        }

    @Test
    fun `get recipes should return error from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = false
            val errorMessage = "Internal server error"
            whenever(recipesUseCase(Unit)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Error(errorMessage))
        }

    @Test
    fun `get bookmark recipes should return recipe list from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = true
            val recipes = FakePresentationData.getRecipes(3)
            `when`(bookmarkUseCase(Unit)).thenReturn(flowOf(recipes))

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Success(recipes))
        }

    @Test
    fun `get bookmark recipes should return empty recipe list from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = true
            val recipes = FakePresentationData.getRecipes(0)
            `when`(bookmarkUseCase(Unit)).thenReturn(flowOf(recipes))

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Success(recipes))
        }

    @Test
    fun `get bookmark recipes should return error from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val isBookmarked = true
            val errorMessage = "Internal server error"
            whenever(bookmarkUseCase(Unit)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            sut.getRecipe(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(RecipeUIModel.Loading)
            verify(observer).onChanged(RecipeUIModel.Error(errorMessage))
        }

    @After
    fun tearDown() {
        sut.onCleared()
    }
}
