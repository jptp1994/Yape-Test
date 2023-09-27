package com.rick_and_morty.domain.interactor.user

import com.rick_and_morty.domain.interactor.BaseUseCase
import com.rick_and_morty.domain.models.Login
import com.rick_and_morty.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetLastUserBaseUseCase = BaseUseCase<Unit, Flow<Login>>

class GetLastUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : GetLastUserBaseUseCase {

    override suspend fun invoke(params: Unit) = loginRepository.getLastUser()
}