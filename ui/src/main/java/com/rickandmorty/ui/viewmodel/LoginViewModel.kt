package com.rickandmorty.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rick_and_morty.domain.interactor.user.ActiveUserUseCase
import com.rick_and_morty.domain.interactor.user.CreateUserUseCase
import com.rick_and_morty.domain.interactor.user.GetCredentialsUserUseCase
import com.rick_and_morty.domain.interactor.user.GetLastUserUseCase
import com.rick_and_morty.domain.interactor.user.GetUserActiveUseCase
import com.rick_and_morty.domain.interactor.user.LogOutUserUseCase
import com.rick_and_morty.domain.models.Credentials
import com.rick_and_morty.domain.models.Login
import com.rickandmorty.ui.utils.CoroutineContextProvider
import com.rickandmorty.ui.utils.ExceptionHandler
import com.rickandmorty.ui.utils.UiAwareLiveData
import com.rickandmorty.ui.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "Login"

sealed class LoginUIModelData : UiAwareModel() {
    data object Loading : LoginUIModelData()
    data class Error(var error: String) : LoginUIModelData()
    data class SuccessLogin(val data: Login) : LoginUIModelData()
    data class SuccessLogout(val data: Login ) : LoginUIModelData()

}

/**
 * if the user is active is redirected to Home
 * if the user is created bring the information and update isActive field
 * if the user not exists create the user and get the last register inserted in the table
 * */
@HiltViewModel
class LoginViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val createUserUseCase: CreateUserUseCase,
    private val getLastUserUseCase: GetLastUserUseCase,

    private val getUserActiveUseCase: GetUserActiveUseCase,

    private val logOutUserUseCase: LogOutUserUseCase,

    private val getCredentialsUserUseCase: GetCredentialsUserUseCase,
    private val activeUserUseCase: ActiveUserUseCase
) : BaseViewModel(contextProvider) {

    private val _login = UiAwareLiveData<LoginUIModelData>()
    private var login: LiveData<LoginUIModelData> = _login

    val isLoading = MutableLiveData<Boolean>()
    fun getCharacter(): LiveData<LoginUIModelData> {
        return login
    }


    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _login.postValue(LoginUIModelData.Error(exception.message?:"Error"))
    }

    private fun createUser(credentials: Credentials) {
        launchCoroutineIO {
            newUser(credentials)
        }
    }

    fun getUserByCredentials(credentials: Credentials){
        _login.postValue(LoginUIModelData.Loading)
        launchCoroutineIO {
            getCredentialsUserUseCase.invoke(credentials.email).collect{
                if (it.email!=""){
                    activeUserUseCase.invoke(it.id)
                    _login.postValue(LoginUIModelData.SuccessLogin(it))
                } else{
                    createUser(credentials)
                }
            }
        }
    }


    private suspend fun newUser(characterId: Credentials) {
        createUserUseCase(characterId).collect {
            getLastUserUseCase(Unit).collect{
                _login.postValue(LoginUIModelData.SuccessLogin(it))
            }
        }
    }

    fun getUserActive(){
        _login.postValue(LoginUIModelData.Loading)
        launchCoroutineIO {
            getUserActiveUseCase.invoke(Unit).collect{
                _login.postValue(LoginUIModelData.SuccessLogin(it))
            }
        }
    }

    fun logOut(idUser:Int) {
        _login.postValue(LoginUIModelData.Loading)
        launchCoroutineIO {
            logOutUserUseCase(idUser).collect {
                    _login.postValue(
                        LoginUIModelData.SuccessLogout(
                            Login(0,"", password = "", userName = "",
                                isActive= false),
                        )
                    )
            }
        }
    }
}
