package com.rickandmorty.data.fakes

import com.rick_and_morty.domain.models.Character
import com.rick_and_morty.domain.models.CharacterLocation
import com.rickandmorty.data.models.CharacterEntity
import com.rickandmorty.data.models.CharacterLocationEntity

object FakeCharacters {
    fun getCharacters(): List<CharacterEntity> = listOf(
        CharacterEntity(
            "01/02/2021",
            "Male",
            1,
            "https://dummyurl.png",
            CharacterLocationEntity("Earth", "https://dummy.url"),
            "Rick",
            "Human",
            "Alive",
            "",
            "",
            false
        ),
        CharacterEntity(
            "01/02/2021",
            "Male",
            2,
            "https://dummyurl.png",
            CharacterLocationEntity("Earth", "https://dummy.url"),
            "Morty",
            "Human",
            "Alive",
            "",
            "",
            false
        )
    )

    fun getCharacter(): Character =
        Character(
            "01/02/2021",
            "Male",
            1,
            "https://dummyurl.png",
            CharacterLocation("Earth", "https://dummy.url"),
            "Rick",
            "Human",
            "Alive",
            "",
            "",
            false
        )
}
