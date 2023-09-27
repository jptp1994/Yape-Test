package com.rickandmorty.cache.mapper

import com.rickandmorty.cache.models.CharacterCacheEntity
import com.rickandmorty.cache.models.LoginCacheEntity
import com.rickandmorty.data.models.CharacterEntity
import com.rickandmorty.data.models.LoginEntity
import javax.inject.Inject

class LoginCacheMapper @Inject constructor() : CacheMapper<LoginCacheEntity, LoginEntity> {
    override fun mapFromCached(type: LoginCacheEntity): LoginEntity {
        return LoginEntity(
            id = type.id,
            email= type.email,
            password = type.password,
            userName = type.userName,
            isActive = type.isActive
        )
    }

    override fun mapToCached(type: LoginEntity): LoginCacheEntity {
        return LoginCacheEntity(
            id = type.id,
            email= type.email,
            password = type.password,
            userName = type.userName,
            isActive = type.isActive
        )
    }
}
