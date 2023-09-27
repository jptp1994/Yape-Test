package com.rickandmorty.data.source

import com.rickandmorty.data.models.LoginEntity
import com.rickandmorty.data.repository.LoginCache
import javax.inject.Inject

class LoginCacheDataSource @Inject constructor(
    private val loginCache: LoginCache
) : LoginCache {

    override suspend fun getUserActive(): LoginEntity? {
       return loginCache.getUserActive()
    }

    override suspend fun getUserCreated(): LoginEntity? {
        return loginCache.getUserCreated()
    }

    override suspend fun makeLogin(email: String, password: String, userName: String) {
        loginCache.makeLogin(email,password,userName)
    }

    override suspend fun logOutUser(idUser: Int) {
        loginCache.logOutUser(idUser)
    }

    override suspend fun activeUser(idUser: Int) {
        loginCache.activeUser(idUser)
    }

    override suspend fun getUserActiveByEmail(email: String): LoginEntity? {
        return loginCache.getUserActiveByEmail(email)
    }
}
