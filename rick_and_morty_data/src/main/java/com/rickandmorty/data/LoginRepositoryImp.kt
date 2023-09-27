package com.rickandmorty.data

import com.rick_and_morty.domain.models.Credentials
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.rick_and_morty.domain.models.Login
import com.rick_and_morty.domain.repository.LoginRepository
import com.rickandmorty.data.mapper.LoginMapper
import com.rickandmorty.data.source.LoginCacheDataSourceFactory
import kotlinx.coroutines.flow.flow

class LoginRepositoryImp @Inject constructor(
    private val loginCacheDataSourceFactory: LoginCacheDataSourceFactory,
    private val loginMapper: LoginMapper,
) : LoginRepository {

    override suspend fun getUserActive(): Flow<Login> = flow {
        val user = loginCacheDataSourceFactory.getDataStore().getUserActive()
        loginMapper.mapFromEntity(user)?.let { emit(it) }
    }

    override suspend fun logOutUser(idUser: Int): Flow<Unit> = flow {
        emit(loginCacheDataSourceFactory.getDataStore().logOutUser(idUser))
    }

    override suspend fun getLastUser(): Flow<Login> = flow {
        loginMapper.mapFromEntity(loginCacheDataSourceFactory.getDataStore().getUserCreated())
            ?.let { emit(it) }
    }

    override suspend fun activeUser(idUser: Int): Flow<Unit> = flow {
        emit(loginCacheDataSourceFactory.getDataStore().activeUser(idUser))
    }

    override suspend fun getUserActiveByEmail(email: String): Flow<Login> = flow {
        loginMapper.mapFromEntity(loginCacheDataSourceFactory.getDataStore()
            .getUserActiveByEmail(email))?.let { emit(it) }
    }

    override suspend fun createUser(credentials: Credentials): Flow<Unit> = flow {
        emit(loginCacheDataSourceFactory.getDataStore().makeLogin(credentials.email,
            credentials.password,credentials.userName))
    }
}
