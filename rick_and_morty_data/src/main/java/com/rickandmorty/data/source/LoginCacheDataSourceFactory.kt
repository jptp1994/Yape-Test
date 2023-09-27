package com.rickandmorty.data.source

import javax.inject.Inject

open class LoginCacheDataSourceFactory @Inject constructor(
    private val loginCacheDataSource: LoginCacheDataSource,
) {

    open suspend fun getDataStore(): LoginCacheDataSource {
        return getRemoteDataSource()
    }

    private fun getRemoteDataSource(): LoginCacheDataSource {
        return loginCacheDataSource
    }
}
