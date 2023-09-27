package com.rick_and_morty.domain.interactor.user

import com.rick_and_morty.domain.interactor.BaseUseCase
import com.rick_and_morty.domain.models.Credentials
import com.rick_and_morty.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias ActiveUserBaseUseCase = BaseUseCase<Int, Flow<Unit>>

class ActiveUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : ActiveUserBaseUseCase {

    override suspend operator fun invoke(params: Int) = loginRepository.activeUser(params)
}