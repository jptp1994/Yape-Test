package com.rickandmorty.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rickandmorty.cache.dao.CharacterDao
import com.rickandmorty.cache.dao.LoginDao
import com.rickandmorty.cache.models.CharacterCacheEntity
import com.rickandmorty.cache.models.CharacterLocationCacheEntity
import com.rickandmorty.cache.models.LoginCacheEntity
import com.rickandmorty.cache.utils.CacheConstants
import com.rickandmorty.cache.utils.Migrations
import javax.inject.Inject

@Database(
    entities = [CharacterCacheEntity::class, CharacterLocationCacheEntity::class, LoginCacheEntity::class],
    version = Migrations.DB_VERSION,
    exportSchema = false
)
abstract class CharactersDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedCharacterDao(): CharacterDao
    abstract fun cachedLoginDao(): LoginDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getInstance(context: Context): CharactersDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CharactersDatabase::class.java,
            CacheConstants.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}
