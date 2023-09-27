package com.rickandmorty.remote.mappers

import com.rickandmorty.remote.models.CharacterLocationModel
import com.rickandmorty.data.models.CharacterLocationEntity
import javax.inject.Inject

class CharacterLocationEntityMapper @Inject constructor() :
    EntityMapper<CharacterLocationModel, CharacterLocationEntity> {
    override fun mapFromModel(model: CharacterLocationModel): CharacterLocationEntity {
        return CharacterLocationEntity(name = model.name, url = model.url)
    }
}
