package com.rick_and_morty.domain.interactor.user

import com.rick_and_morty.domain.interactor.BaseUseCase
import com.rick_and_morty.domain.interactor.GetUserActiveBaseUseCase
import com.rick_and_morty.domain.models.Login
import com.rick_and_morty.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetUserActiveBaseUseCase = BaseUseCase<Unit, Flow<Login>>

class GetUserActiveUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : GetUserActiveBaseUseCase {

    override suspend operator fun invoke(params: Unit) = loginRepository.getUserActive()
}