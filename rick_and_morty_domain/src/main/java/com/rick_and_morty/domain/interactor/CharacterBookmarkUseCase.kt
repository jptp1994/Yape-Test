package com.rick_and_morty.domain.interactor

import com.rick_and_morty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias CharacterBookmarkBaseUseCase = BaseUseCase<Long, Flow<Int>>

class CharacterBookmarkUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : CharacterBookmarkBaseUseCase {

    override suspend operator fun invoke(params: Long) =
        characterRepository.setCharacterBookmarked(params)
}
