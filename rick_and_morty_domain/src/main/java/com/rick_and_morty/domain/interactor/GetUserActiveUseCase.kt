package com.rick_and_morty.domain.interactor

import com.rick_and_morty.domain.models.Login
import kotlinx.coroutines.flow.Flow

typealias GetUserActiveBaseUseCase = BaseUseCase<Unit, Flow<Login>>

