package com.rickandmorty.data.repository

import com.rickandmorty.data.models.LoginEntity

interface LoginCache {
    // Cache
    suspend fun getUserActive(): LoginEntity?
    suspend fun getUserCreated(): LoginEntity?
    suspend fun makeLogin(email:String, password:String, userName:String)
    suspend fun logOutUser(idUser: Int)

    // set the active user selected
    suspend fun activeUser(idUser: Int)

    // get the user active by email
    suspend fun getUserActiveByEmail(email:String): LoginEntity?
}
