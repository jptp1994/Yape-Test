package com.rickandmorty.cache.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickandmorty.cache.utils.CacheConstants

@Entity(tableName = CacheConstants.LOGIN_TABLE_NAME)
data class LoginCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password:String,
    val userName: String,
    val isActive: Boolean = true,
)
