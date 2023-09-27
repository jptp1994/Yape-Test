package com.rickandmorty.data.models

data class LoginEntity(
    val id: Int = 0,
    val email: String,
    val password:String,
    val userName: String,
    val isActive: Boolean = true,
)
