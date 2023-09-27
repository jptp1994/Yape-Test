package com.rick_and_morty.domain.interactor.user

import com.rick_and_morty.domain.interactor.BaseUseCase
import com.rick_and_morty.domain.models.Login
import com.rick_and_morty.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


typealias GetCredentialsUserBaseUseCase = BaseUseCase<String, Flow<Login?>>

class GetCredentialsUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : GetCredentialsUserBaseUseCase {

    override suspend fun invoke(params: String) = loginRepository.getUserActiveByEmail(params)
}