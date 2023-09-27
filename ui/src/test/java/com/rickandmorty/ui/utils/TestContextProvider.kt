package com.rickandmorty.ui.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider {
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
    val testScope: TestScope = TestScope(dispatcher)

    override val io: CoroutineDispatcher = dispatcher

    override val default: CoroutineDispatcher = dispatcher

    override val main: CoroutineDispatcher = dispatcher
}
