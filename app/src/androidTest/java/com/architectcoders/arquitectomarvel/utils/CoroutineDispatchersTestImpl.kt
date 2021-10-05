package com.architectcoders.arquitectomarvel.utils

import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CoroutineDispatchersTestImpl @Inject constructor() : CoroutineDispatchers {
    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}
