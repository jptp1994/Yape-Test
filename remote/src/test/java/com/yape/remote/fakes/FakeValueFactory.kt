package com.yape.remote.fakes

import java.util.UUID
import kotlin.random.Random

object FakeValueFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomLong(): Long {
        return Random.nextLong()
    }

}
