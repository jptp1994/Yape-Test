package com.yape.cache.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import androidx.room.TypeConverters
import com.yape.cache.converters.Converters
import com.yape.cache.utils.CacheConstants

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = CacheConstants.RECIPE_TABLE_NAME)
data class RecipeCacheEntity(
    @PrimaryKey
    val id: Long,
    val image: String,
    @Embedded
    val recipeLocation: RecipeLocationCacheEntity,
    val name: String,
    val description: String,
    val difficult: String,
    val time: String,
    @TypeConverters(Converters::class)
    val ingredients: List<RecipeCacheIngredientEntity>,
    val tutorial: String,
    @ColumnInfo(name = "is_bookmarked")
    val isBookMarked: Boolean
)
