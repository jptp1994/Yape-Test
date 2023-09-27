package com.rickandmorty.remote.models

import com.rickandmorty.remote.models.CharacterModel
import com.squareup.moshi.Json

data class CharacterResponseModel(
    @field:Json(name = "results")
    val characters: List<CharacterModel>
)
