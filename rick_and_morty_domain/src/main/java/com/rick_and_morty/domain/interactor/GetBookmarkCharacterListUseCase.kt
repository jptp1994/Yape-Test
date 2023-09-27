package com.rick_and_morty.domain.interactor

import com.rick_and_morty.domain.models.Character
import com.rick_and_morty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetBookmarkCharacterListBaseUseCase = BaseUseCase<Unit, Flow<List<Character>>>

class GetBookmarkCharacterListUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetBookmarkCharacterListBaseUseCase {

    override suspend operator fun invoke(params: Unit) = characterRepository.getBookMarkedCharacters()
}
