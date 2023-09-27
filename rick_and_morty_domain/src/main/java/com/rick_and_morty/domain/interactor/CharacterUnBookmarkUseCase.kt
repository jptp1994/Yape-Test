package com.rick_and_morty.domain.interactor

import com.rick_and_morty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias CharacterUnBookmarkBaseUseCase = BaseUseCase<Long, Flow<Int>>

class CharacterUnBookmarkUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : CharacterUnBookmarkBaseUseCase {

    override suspend operator fun invoke(params: Long) =
        characterRepository.setCharacterUnBookMarked(params)
}
