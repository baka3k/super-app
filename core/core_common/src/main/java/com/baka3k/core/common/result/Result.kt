package com.baka3k.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }.onEmpty {
        }
        .onStart {
            emit(Result.Loading)
        }
        .catch { emit(Result.Error(it)) }
}

inline fun <R> runCatching(block: () -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Error(e)
    }
}
