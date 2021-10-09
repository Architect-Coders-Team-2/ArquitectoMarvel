package com.architectcoders.usecases

interface InvokeUseCase<T, U> {
    suspend fun invoke(param: T): U?
}
