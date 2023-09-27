package com.rickandmorty.ui.viewmodel

import androidx.lifecycle.LiveData
import com.rick_and_morty.domain.interactor.GetBookmarkCharacterListUseCase
import com.rick_and_morty.domain.interactor.GetCharacterListUseCase
import com.rick_and_morty.domain.models.Character
import com.rickandmorty.ui.utils.CoroutineContextProvider
import com.rickandmorty.ui.utils.ExceptionHandler
import com.rickandmorty.ui.utils.UiAwareLiveData
import com.rickandmorty.ui.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "CharacterListViewModel"

sealed class CharacterUIModel : UiAwareModel() {
    data object Loading : CharacterUIModel()
    data class Error(var error: String) : CharacterUIModel()
    data class Success(val data: List<Character>) : CharacterUIModel()
}

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val characterListUseCase: GetCharacterListUseCase,
    private val bookmarkCharacterListUseCase: GetBookmarkCharacterListUseCase
) : BaseViewModel(contextProvider) {

    private val _characterList = UiAwareLiveData<CharacterUIModel>()
    private var characterList: LiveData<CharacterUIModel> = _characterList

    fun getCharacters(): LiveData<CharacterUIModel> {
        return characterList
    }

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _characterList.postValue(CharacterUIModel.Error(exception.toString()))
    }

    fun getCharacters(isFavorite: Boolean) {
        _characterList.postValue(CharacterUIModel.Loading)
        launchCoroutineIO {
            if (isFavorite)
                loadFavoriteCharacters()
            else
                loadCharacters()
        }
    }

    private suspend fun loadCharacters() {
        characterListUseCase(Unit).collect {
            _characterList.postValue(CharacterUIModel.Success(it))
        }
    }

    private suspend fun loadFavoriteCharacters() {
        bookmarkCharacterListUseCase(Unit).collect {
            _characterList.postValue(CharacterUIModel.Success(it))
        }
    }
}
