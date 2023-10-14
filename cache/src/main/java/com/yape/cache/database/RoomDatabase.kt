package com.yape.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yape.cache.converters.Converters
import com.yape.cache.dao.RecipeDao
import com.yape.cache.models.RecipeCacheEntity
import com.yape.cache.models.RecipeLocationCacheEntity
import com.yape.cache.utils.CacheConstants
import com.yape.cache.utils.Migrations
import javax.inject.Inject

@Database(entities = [RecipeCacheEntity::class, RecipeLocationCacheEntity::class], version = Migrations.DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class YapeDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: YapeDatabase? = null

        fun getInstance(context: Context): YapeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            YapeDatabase::class.java,
            CacheConstants.DB_NAME
        ).addTypeConverter(Converters()).fallbackToDestructiveMigration().build()
    }
}
