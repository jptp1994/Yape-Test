package com.yape.cache.utils

import androidx.room.migration.Migration

class Migrations {
    companion object {
        const val DB_VERSION = 5

        fun getMigrations(): Array<Migration> {
            return arrayOf()
        }
    }
}
