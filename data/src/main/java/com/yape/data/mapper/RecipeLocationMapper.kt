package com.yape.data.mapper

import com.yape.data.models.RecipeLocationEntity
import com.yape.domain.models.RecipeLocation
import javax.inject.Inject

class RecipeLocationMapper @Inject constructor() :
    Mapper<RecipeLocationEntity, RecipeLocation> {

    override fun mapFromEntity(type: RecipeLocationEntity): RecipeLocation {
        return RecipeLocation(latitude = type.latitude, longitude = type.longitude)
    }

    override fun mapToEntity(type: RecipeLocation): RecipeLocationEntity {
        return RecipeLocationEntity(latitude = type.latitude, longitude = type.longitude)
    }
}
