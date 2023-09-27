package com.rick_and_morty.domain.repository

import com.rick_and_morty.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    // Remote and cache
    suspend fun getCharacters(): Flow<List<Character>>
    suspend fun getCharacter(characterId: Long): Flow<Character>

    // Cache
    suspend fun saveCharacters(listCharacters: List<Character>)
    suspend fun getBookMarkedCharacters(): Flow<List<Character>>
    suspend fun setCharacterBookmarked(characterId: Long): Flow<Int>
    suspend fun setCharacterUnBookMarked(characterId: Long): Flow<Int>
}
