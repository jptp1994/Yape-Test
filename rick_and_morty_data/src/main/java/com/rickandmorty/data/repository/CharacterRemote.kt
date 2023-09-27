package com.rickandmorty.data.repository

import com.rickandmorty.data.models.CharacterEntity

interface CharacterRemote {
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getCharacter(characterId: Long): CharacterEntity
}
