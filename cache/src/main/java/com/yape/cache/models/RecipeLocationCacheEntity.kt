package com.yape.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yape.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.LOCATION_TABLE_NAME)
data class RecipeLocationCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String
)
