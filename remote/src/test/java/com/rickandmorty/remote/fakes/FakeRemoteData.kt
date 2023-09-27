package com.rickandmorty.remote.fakes

import com.rickandmorty.remote.fakes.FakeValueFactory.randomInt
import com.rickandmorty.remote.fakes.FakeValueFactory.randomString
import com.rickandmorty.data.models.CharacterEntity
import com.rickandmorty.data.models.CharacterLocationEntity
import com.rickandmorty.remote.models.CharacterLocationModel
import com.rickandmorty.remote.models.CharacterModel
import com.rickandmorty.remote.models.CharacterResponseModel

object FakeRemoteData {

    fun getResponse(size: Int, isRandomId: Boolean = true): CharacterResponseModel {
        return CharacterResponseModel(getFakeCharacterModel(size, isRandomId))
    }

    private fun getFakeCharacterModel(size: Int, isRandomId: Boolean): List<CharacterModel> {
        val characters = mutableListOf<CharacterModel>()
        repeat(size) {
            characters.add(getCharacterModel(isRandomId))
        }
        return characters
    }

    fun getCharacterModel(isRandomId: Boolean): CharacterModel {
        return CharacterModel(
            created = randomString(),
            gender = randomString(),
            id = if (isRandomId) randomInt() else 1,
            image = randomString(),
            characterLocation = CharacterLocationModel(
                name = randomString(),
                url = randomString()
            ),
            name = randomString(),
            species = randomString(),
            status = randomString(),
            type = randomString(),
            url = randomString(),
            isBookMarked = false
        )
    }

    fun getCharacterEntity(isRandomId: Boolean): CharacterEntity {
        return CharacterEntity(
            created = randomString(),
            gender = randomString(),
            id = if (isRandomId) randomInt() else 1,
            image = randomString(),
            characterLocation = CharacterLocationEntity(
                name = randomString(),
                url = randomString()
            ),
            name = randomString(),
            species = randomString(),
            status = randomString(),
            type = randomString(),
            url = randomString(),
            isBookMarked = false
        )
    }
}
