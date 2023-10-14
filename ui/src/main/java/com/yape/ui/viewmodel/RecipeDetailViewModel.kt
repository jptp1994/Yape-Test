package com.yape.ui.viewmodel

import androidx.lifecycle.LiveData
import com.yape.domain.interactor.RecipeBookmarkUseCase
import com.yape.domain.interactor.RecipeUnBookmarkUseCase
import com.yape.domain.interactor.GetRecipeByIdUseCase
import com.yape.domain.models.Recipe
import com.yape.ui.utils.CoroutineContextProvider
import com.yape.ui.utils.ExceptionHandler
import com.yape.ui.utils.UiAwareLiveData
import com.yape.ui.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

private const val TAG = "RecipeDetailVM"

sealed class RecipeDetailUIModel : UiAwareModel() {
    data object Loading : RecipeDetailUIModel()
    data class Error(var error: String) : RecipeDetailUIModel()
    data class Success(val data: Recipe) : RecipeDetailUIModel()
    data class BookMarkStatus(val bookmark: Bookmark, val status: Boolean) :
        RecipeDetailUIModel()
}

enum class Bookmark {
    BOOKMARK,
    UN_BOOKMARK
}

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val recipeByIdUseCase: GetRecipeByIdUseCase,
    private val bookmarkUserCase: RecipeBookmarkUseCase,
    private val unBookmarkUserCase: RecipeUnBookmarkUseCase
) : BaseViewModel(contextProvider) {

    private val _recipe = UiAwareLiveData<RecipeDetailUIModel>()
    private var recipe: LiveData<RecipeDetailUIModel> = _recipe

    fun getRecipe(): LiveData<RecipeDetailUIModel> {
        return recipe
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _recipe.postValue(RecipeDetailUIModel.Error(exception.message?:"Error"))
    }

    fun getRecipeDetail(recipeID: Long) {
        _recipe.postValue(RecipeDetailUIModel.Loading)
        launchCoroutineIO {
            loadRecipe(recipeID)
        }
    }

    private suspend fun loadRecipe(recipeID: Long) {
        recipeByIdUseCase(recipeID).collect {
            _recipe.postValue(RecipeDetailUIModel.Success(it))
        }
    }

    fun setBookmarkRecipe(recipeID: Long) {
        launchCoroutineIO {
            bookmarkUserCase(recipeID).collect {
                if (it == 1)
                    _recipe.postValue(
                        RecipeDetailUIModel.BookMarkStatus(
                            Bookmark.BOOKMARK,
                            true
                        )
                    )
                else
                    _recipe.postValue(
                        RecipeDetailUIModel.BookMarkStatus(
                            Bookmark.BOOKMARK,
                            false
                        )
                    )
            }
        }
    }

    fun setUnBookmarkRecipe(recipeID: Long) {
        launchCoroutineIO {
            unBookmarkUserCase(recipeID).collect {
                if (it == 1)
                    _recipe.postValue(
                        RecipeDetailUIModel.BookMarkStatus(
                            Bookmark.UN_BOOKMARK,
                            true
                        )
                    )
                else
                    _recipe.postValue(
                        RecipeDetailUIModel.BookMarkStatus(
                            Bookmark.UN_BOOKMARK,
                            false
                        )
                    )
            }
        }
    }
}
