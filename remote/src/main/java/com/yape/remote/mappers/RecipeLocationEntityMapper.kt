package com.yape.remote.mappers

import com.yape.remote.models.RecipeLocationModel
import com.yape.data.models.RecipeLocationEntity
import javax.inject.Inject

class RecipeLocationEntityMapper @Inject constructor() :
    EntityMapper<RecipeLocationModel, RecipeLocationEntity> {
    override fun mapFromModel(model: RecipeLocationModel): RecipeLocationEntity {
        return RecipeLocationEntity(latitude = model.latitude, longitude = model.longitude)
    }
}
