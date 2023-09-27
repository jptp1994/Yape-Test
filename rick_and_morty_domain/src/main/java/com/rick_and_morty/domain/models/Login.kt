package com.rick_and_morty.domain.models

data class Login(
    val id: Int,
    val email: String,
    val password:String,
    val userName: String,
    val isActive: Boolean,
)
