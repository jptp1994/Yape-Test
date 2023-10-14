package com.yape.remote.mappers

interface EntityMapper<M, E> {

    fun mapFromModel(model: M): E
}
