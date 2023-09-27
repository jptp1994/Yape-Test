package com.rickandmorty.cache.utils

import androidx.room.migration.Migration

class Migrations {
    companion object {
        const val DB_VERSION = 3

        fun getMigrations(): Array<Migration> {
            return arrayOf()
        }
    }
}
