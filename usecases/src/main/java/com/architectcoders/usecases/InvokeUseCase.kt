package com.architectcoders.usecases

interface InvokeUseCase<T, U> {
    suspend fun invoke(vararg param: T): U?
}
