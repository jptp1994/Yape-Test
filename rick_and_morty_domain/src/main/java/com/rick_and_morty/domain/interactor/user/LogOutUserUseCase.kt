package com.rick_and_morty.domain.interactor.user

import com.rick_and_morty.domain.interactor.BaseUseCase
import com.rick_and_morty.domain.interactor.LogOutUserBaseUseCase
import com.rick_and_morty.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LogOutUserBaseUseCase = BaseUseCase<Int, Flow<Unit>>

class LogOutUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : LogOutUserBaseUseCase {

    override suspend operator fun invoke(params: Int) = loginRepository.logOutUser(params)
}