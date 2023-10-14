package com.yape.ui.viewmodel

import androidx.lifecycle.LiveData
import com.yape.domain.interactor.GetBookmarkRecipeListUseCase
import com.yape.domain.interactor.GetRecipeListUseCase
import com.yape.domain.models.Recipe
import com.yape.ui.utils.CoroutineContextProvider
import com.yape.ui.utils.ExceptionHandler
import com.yape.ui.utils.UiAwareLiveData
import com.yape.ui.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

private const val TAG = "RecipeListViewModel"

sealed class RecipeUIModel : UiAwareModel() {
    data object Loading : RecipeUIModel()
    data class Error(var error: String) : RecipeUIModel()
    data class Success(val data: List<Recipe>) : RecipeUIModel()
}

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val recipeListUseCase: GetRecipeListUseCase,
    private val getBookmarkRecipeListUseCase: GetBookmarkRecipeListUseCase
) : BaseViewModel(contextProvider) {

    private val _recipeList = UiAwareLiveData<RecipeUIModel>()
    private var recipeList: LiveData<RecipeUIModel> = _recipeList

    fun getRecipe(): LiveData<RecipeUIModel> {
        return recipeList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _recipeList.postValue(RecipeUIModel.Error(exception.toString()))
    }
//    java.lang.NullPointerException: Parameter specified as non-null is null: method com.yape.data.models.RecipeEntity.<init>, parameter difficult
//    java.lang.NullPointerException: Parameter specified as non-null is null: method com.yape.data.models.RecipeLocationEntity.<init>, parameter latitude
//    java.lang.NullPointerException: Parameter specified as non-null is null: method com.yape.data.models.RecipeLocationEntity.<init>, parameter latitude
//    java.net.UnknownServiceException: CLEARTEXT communication to demo6953268.mockable.io not permitted by network security policy
    fun getRecipe(isFavorite: Boolean) {
        _recipeList.postValue(RecipeUIModel.Loading)
        launchCoroutineIO {
            if (isFavorite)
                loadFavoriteRecipe()
            else
                loadRecipe()
        }
    }

    private suspend fun loadRecipe() {
        recipeListUseCase(Unit).collect {
            _recipeList.postValue(RecipeUIModel.Success(it))
        }
    }

    private suspend fun loadFavoriteRecipe() {
        getBookmarkRecipeListUseCase(Unit).collect {
            _recipeList.postValue(RecipeUIModel.Success(it))
        }
    }
}
