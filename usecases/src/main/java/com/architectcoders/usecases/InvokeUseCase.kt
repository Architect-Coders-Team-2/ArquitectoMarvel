package com.architectcoders.usecases

interface InvokeUseCase<T, U> {
    suspend operator fun invoke(param: T): U?
}
