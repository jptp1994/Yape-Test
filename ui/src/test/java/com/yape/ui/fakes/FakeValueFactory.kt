package com.yape.ui.fakes

import java.util.UUID
import kotlin.random.Random

object FakeValueFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return Random.nextInt()
    }

    fun randomLong(): Long {
        return Random.nextLong()
    }

    fun randomBoolean(): Boolean {
        return Random.nextBoolean()
    }
}
