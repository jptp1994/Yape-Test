package com.rick_and_morty.domain.interactor

import kotlinx.coroutines.flow.Flow

typealias LogOutUserBaseUseCase = BaseUseCase<Int, Flow<Unit>>

