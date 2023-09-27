package com.rickandmorty.remote.repository

import com.rickandmorty.data.models.CharacterEntity
import com.rickandmorty.data.repository.CharacterRemote
import com.rickandmorty.remote.api.CharacterService
import com.rickandmorty.remote.mappers.CharacterEntityMapper
import javax.inject.Inject

class CharacterRemoteImp @Inject constructor(
    private val characterService: CharacterService,
    private val characterEntityMapper: CharacterEntityMapper
) : CharacterRemote {

    override suspend fun getCharacters(): List<CharacterEntity> {
        return characterService.getCharacters().characters.map { characterModel ->
            characterEntityMapper.mapFromModel(characterModel)
        }
    }

    override suspend fun getCharacter(characterId: Long): CharacterEntity {
        return characterEntityMapper.mapFromModel(characterService.getCharacter(characterId))
    }
}
