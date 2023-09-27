package com.rickandmorty.cache

import com.rickandmorty.cache.dao.LoginDao
import com.rickandmorty.cache.mapper.LoginCacheMapper
import com.rickandmorty.cache.models.LoginCacheEntity
import com.rickandmorty.data.models.LoginEntity
import com.rickandmorty.data.repository.LoginCache
import javax.inject.Inject

class LoginCacheImp @Inject constructor(
    private val loginDao: LoginDao,
    private val loginCacheMapper: LoginCacheMapper,
) : LoginCache {


    override suspend fun getUserActive(): LoginEntity? {
        return loginDao.getUserActive()?.let { loginCacheMapper.mapFromCached(it) }
    }

    override suspend fun getUserCreated(): LoginEntity? {
        return loginDao.getLastCharacter()?.let { loginCacheMapper.mapFromCached(it) }
    }

    override suspend fun makeLogin(email: String, password: String, userName:String){
       loginDao.addUser(LoginCacheEntity(email= email,password= password, userName = userName))
    }

    override suspend fun logOutUser(idUser: Int) {
        loginDao.getLogOut(idUser)
    }

    override suspend fun activeUser(idUser: Int) {
        loginDao.activeUser(idUser)
    }

    override suspend fun getUserActiveByEmail(email: String): LoginEntity? {
        return loginDao.getCredentialUser(email)?.let { loginCacheMapper.mapFromCached(it) }
    }

}
