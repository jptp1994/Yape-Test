package com.rick_and_morty.domain.repository

import com.rick_and_morty.domain.models.Character
import com.rick_and_morty.domain.models.Credentials
import com.rick_and_morty.domain.models.Login
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    // Remote and cache
    //This get the last userActive
    suspend fun getUserActive(): Flow<Login>
    // this disabled the state of the actual user
    suspend fun logOutUser(idUser: Int): Flow<Unit>
    // get the last created User
    suspend fun getLastUser(): Flow<Login>

    // set the active user selected
    suspend fun activeUser(idUser: Int): Flow<Unit>

    // get the user active by email
    suspend fun getUserActiveByEmail(email:String): Flow<Login>

    // create a new user
    suspend fun createUser(credentials:Credentials): Flow<Unit>
}