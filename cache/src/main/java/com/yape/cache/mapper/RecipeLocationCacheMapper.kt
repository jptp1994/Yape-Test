package com.yape.cache.mapper

import com.yape.cache.models.RecipeLocationCacheEntity
import com.yape.data.models.RecipeLocationEntity
import javax.inject.Inject

class RecipeLocationCacheMapper @Inject constructor() :
    CacheMapper<RecipeLocationCacheEntity, RecipeLocationEntity> {
    override fun mapFromCached(type: RecipeLocationCacheEntity): RecipeLocationEntity {
        return RecipeLocationEntity(latitude = type.latitude, longitude = type.longitude)
    }

    override fun mapToCached(type: RecipeLocationEntity): RecipeLocationCacheEntity {
        return RecipeLocationCacheEntity(latitude = type.latitude, longitude = type.longitude)
    }
}
