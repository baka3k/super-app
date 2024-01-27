package com.baka3k.core.common.interactor

interface SingleUseCase<T> {
    suspend operator fun invoke(): T
}

interface SingleUseCaseWithParameter<in P, out R> {
    suspend operator fun invoke(input: P): R
}

interface UpStreamSingleUseCaseParameter<in P, out R> {
    operator fun invoke(input: P): R
}

interface UpStreamSingleUseCase<out R> {
    operator fun invoke(): R
}
