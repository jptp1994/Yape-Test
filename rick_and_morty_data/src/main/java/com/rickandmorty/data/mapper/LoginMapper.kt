package com.rickandmorty.data.mapper

import com.rickandmorty.data.models.CharacterEntity
import javax.inject.Inject
import com.rick_and_morty.domain.models.Character
import com.rick_and_morty.domain.models.Login
import com.rickandmorty.data.models.LoginEntity

class LoginMapper @Inject constructor() : Mapper<LoginEntity?, Login?> {

    override fun mapFromEntity(type: LoginEntity?): Login? {
        return type?.let {
            Login(
                id = it.id,
                email =it.email,
                password = it.password,
                userName = it.userName,
                isActive = it.isActive
            )
        }
    }

    override fun mapToEntity(type: Login?): LoginEntity? {
        return type?.let {
            LoginEntity(
                id = it.id,
                email = it.email,
                password = it.password,
                userName = it.userName,
                isActive = it.isActive
            )
        }
    }
}
