package com.rickandmorty.cache.mapper

import com.rickandmorty.cache.models.CharacterLocationCacheEntity
import com.rickandmorty.data.models.CharacterLocationEntity
import javax.inject.Inject

class CharacterLocationCacheMapper @Inject constructor() :
    CacheMapper<CharacterLocationCacheEntity, CharacterLocationEntity> {
    override fun mapFromCached(type: CharacterLocationCacheEntity): CharacterLocationEntity {
        return CharacterLocationEntity(name = type.name, url = type.url)
    }

    override fun mapToCached(type: CharacterLocationEntity): CharacterLocationCacheEntity {
        return CharacterLocationCacheEntity(name = type.name, url = type.url)
    }
}
