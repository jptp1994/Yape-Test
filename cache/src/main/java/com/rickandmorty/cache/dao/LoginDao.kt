package com.rickandmorty.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickandmorty.cache.models.CharacterCacheEntity
import com.rickandmorty.cache.models.LoginCacheEntity

@Dao
interface LoginDao {

    @Query("SELECT * FROM login where isActive = 1 Limit 1")
    fun getUserActive(): LoginCacheEntity? = null

    @Query("SELECT * FROM login ORDER BY id DESC Limit 1")
    fun getLastCharacter(): LoginCacheEntity? = null

    @Query("SELECT * FROM login where email= :email Limit 1")
    fun getCredentialUser(email:String): LoginCacheEntity ?= null

    @Query("UPDATE login SET isActive = 1 WHERE id = :id")
    fun activeUser(id: Int)


    @Query("UPDATE login SET isActive = 0 WHERE id = :id")
    fun getLogOut(id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(vararg character: LoginCacheEntity)
}
